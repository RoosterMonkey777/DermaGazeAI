package khan.z.dermagazeai.registration.helpers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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

                        // Save login state
                        val sharedPref = fragment.requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        sharedPref.edit().putBoolean("isLoggedIn", true).apply()

                        // Clear back stack and navigate to HomeFragment
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true) // Remove LoginFragment from back stack
                            .build()
                        navController.navigate(R.id.action_loginFragment_to_homeFragment, null, navOptions)
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
