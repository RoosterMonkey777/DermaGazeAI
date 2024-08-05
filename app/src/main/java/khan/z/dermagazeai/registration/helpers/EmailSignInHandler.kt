package khan.z.dermagazeai.registration.helpers

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import khan.z.dermagazeai.R

class EmailSignInHandler(
    private val fragment: Fragment,
    private val navController: NavController
) {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    fun initializeEmailSignIn(view: View, emailEditTextId: Int, passwordEditTextId: Int, loginButtonId: Int) {
        etEmail = view.findViewById(emailEditTextId)
        etPassword = view.findViewById(passwordEditTextId)

        val loginButton: Button = view.findViewById(loginButtonId)
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        Amplify.Auth.signIn(email, password,
            { result ->
                fragment.requireActivity().runOnUiThread {
                    if (result.isSignedIn) {
                        Toast.makeText(fragment.context, "Login successful", Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(fragment.context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                fragment.requireActivity().runOnUiThread {
                    Toast.makeText(fragment.context, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginFailed", error.toString())
                }
            }
        )
    }
}
