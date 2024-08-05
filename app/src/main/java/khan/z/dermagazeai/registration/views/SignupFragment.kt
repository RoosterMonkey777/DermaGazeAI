package khan.z.dermagazeai.registration.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.helpers.FacebookSignInHandler
import khan.z.dermagazeai.registration.helpers.GoogleSignInHandler
import khan.z.dermagazeai.registration.helpers.EmailSignUpHandler


//V3: email signup and google, fb
// V2 : Seperation of concerns
class SignupFragment : Fragment() {

    private lateinit var googleSignInHandler: GoogleSignInHandler
    private lateinit var facebookSignInHandler: FacebookSignInHandler
    private lateinit var signupHandlerEmail: EmailSignUpHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInHandler = GoogleSignInHandler(this, findNavController(), R.id.action_signupFragment_to_homeFragment)
        googleSignInHandler.initializeGoogleSignIn(view, getString(R.string.google_app_id), R.id.btn_google, R.id.custom_google)

        facebookSignInHandler = FacebookSignInHandler(this, findNavController(), R.id.action_signupFragment_to_homeFragment)
        facebookSignInHandler.initializeFacebookLogin(view, R.id.btn_fb, R.id.custom_fb)

        signupHandlerEmail = EmailSignUpHandler(this, findNavController())
        signupHandlerEmail.initializeSignup(view, R.id.et_email, R.id.et_password, R.id.et_confirm_password, R.id.btn_signup)

        view.findViewById<TextView>(R.id.tv_login).setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignInHandler.onActivityResult(requestCode, resultCode, data)
        facebookSignInHandler.onActivityResult(requestCode, resultCode, data)
    }
}

//// V2 : Seperation of concerns
//class SignupFragment : Fragment() {
//
//    private lateinit var signupHandler: SignUpHandler
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_signup, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        signupHandler = SignUpHandler(this, findNavController())
//        signupHandler.initializeSignup(view, R.id.et_email, R.id.et_password, R.id.et_confirm_password, R.id.btn_signup)
//
//        view.findViewById<TextView>(R.id.tv_login).setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
//        }
//    }
//}



// V1 - just email sign up
//class SignupFragment : Fragment() {
//
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_signup, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        etEmail = view.findViewById(R.id.et_email)
//        etPassword = view.findViewById(R.id.et_password)
//        etConfirmPassword = view.findViewById(R.id.et_confirm_password)
//
//        view.findViewById<Button>(R.id.btn_signup).setOnClickListener {
//            if (validateInput()) {
//                signup()
//            }
//        }
//
//        val loginTextView: TextView = view.findViewById(R.id.tv_login)
//        loginTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
//        }
//
//    }
//
//    private fun validateInput(): Boolean {
//        val email = etEmail.text.toString()
//        val password = etPassword.text.toString()
//        val confirmPassword = etConfirmPassword.text.toString()
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
//            return false
//        }
//        if (password != confirmPassword) {
//            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
//            return false
//        }
//        if (password.length < 8) {
//            Toast.makeText(context, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
//            return false
//        }
//        return true
//    }
//
//    private fun signup() {
//        val email = etEmail.text.toString()
//        val password = etPassword.text.toString()
//
//        val options = AuthSignUpOptions.builder()
//            .userAttribute(AuthUserAttributeKey.email(), email)
//            .build()
//
//        Amplify.Auth.signUp(email, password, options,
//            { result ->
//                activity?.runOnUiThread {
//                    if (result.isSignUpComplete) {
//                        Toast.makeText(context, "Sign up succeeded", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(context, "A verification code has been sent to your email", Toast.LENGTH_LONG).show()
//                        val bundle = Bundle().apply {
//                            putString("email", email)
//                        }
//                        findNavController().navigate(R.id.action_signupFragment_to_confirmationFragment, bundle)
//                    }
//                }
//            },
//            { error ->
//                activity?.runOnUiThread {
//                    val errorMessage = error.message ?: "Sign up failed"
//                    if (errorMessage.contains("Username already exists")) {
//                        Toast.makeText(context, "Email already in use", Toast.LENGTH_LONG).show()
//                    } else {
//                        Toast.makeText(context, "Sign up failed: $errorMessage", Toast.LENGTH_SHORT).show()
//                    }
//                    Log.e("SignUpFailed", error.toString())
//                }
//            }
//        )
//    }
//
//
//
//
//}