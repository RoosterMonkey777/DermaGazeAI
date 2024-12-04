package khan.z.dermagazeai.registration.helpers

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import khan.z.dermagazeai.R

class EmailSignUpHandler(
    private val fragment: Fragment,
    private val navController: NavController
) {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    fun initializeSignup(view: View, emailEditTextId: Int, passwordEditTextId: Int, confirmPasswordEditTextId: Int, signupButtonId: Int) {
        etEmail = view.findViewById(emailEditTextId)
        etPassword = view.findViewById(passwordEditTextId)
        etConfirmPassword = view.findViewById(confirmPasswordEditTextId)

        val signupButton: Button = view.findViewById(signupButtonId)
        signupButton.setOnClickListener {
            if (validateInput()) {
                signup()
            }
        }
    }

    // New performSignUp method for direct calls
    fun performSignUp(email: String, password: String, confirmPassword: String) {
        etEmail.setText(email)
        etPassword.setText(password)
        etConfirmPassword.setText(confirmPassword)

        if (validateInput()) {
            signup()
        }
    }

    private fun validateInput(): Boolean {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(fragment.context, "Invalid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(fragment.context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 8) {
            Toast.makeText(fragment.context, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun signup() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()

        Amplify.Auth.signUp(email, password, options,
            { result ->
                fragment.requireActivity().runOnUiThread {
                    if (result.isSignUpComplete) {
                        Toast.makeText(fragment.context, "Sign up succeeded", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(fragment.context, "Verification code sent to your email", Toast.LENGTH_LONG).show()
                        val bundle = Bundle().apply {
                            putString("email", email)
                        }
                        navController.navigate(R.id.action_signupFragment_to_confirmationFragment, bundle)
                    }
                }
            },
            { error ->
                fragment.requireActivity().runOnUiThread {
                    val errorMessage = error.message ?: "Sign up failed"
                    if (errorMessage.contains("Username already exists")) {
                        Toast.makeText(fragment.context, "Email already in use", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(fragment.context, "Sign up failed: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("SignUpFailed", error.toString())
                }
            }
        )
    }
}
