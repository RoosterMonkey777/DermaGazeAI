package khan.z.dermagazeai.registration.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.Identity
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.SignInMethod
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import khan.z.dermagazeai.registration.helpers.FacebookSignInHandler
import khan.z.dermagazeai.registration.helpers.GoogleSignInHandler


// CURRENT VERSION ----------------------------------------------------------------------------------------

// V4: both google and facebook signin with seperation of concerns
class LoginFragment : Fragment() {

    private lateinit var googleSignInHandler: GoogleSignInHandler
    private lateinit var facebookSignInHandler: FacebookSignInHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleSignInHandler = GoogleSignInHandler(this, findNavController())
        googleSignInHandler.initializeGoogleSignIn(getString(R.string.google_app_id))
        facebookSignInHandler = FacebookSignInHandler(this, findNavController())
        facebookSignInHandler.initializeFacebookLogin(view, R.id.btn_fb)

        view.findViewById<View>(R.id.btn_google).setOnClickListener {
            googleSignInHandler.signIn()
        }
//        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
//            signOut()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignInHandler.onActivityResult(requestCode, resultCode, data)
        facebookSignInHandler.onActivityResult(requestCode, resultCode, data)
    }

//    private fun signOut() {
//        Log.d("LoginFragment", "Sign out initiated")
//
//        val oneTapClient = Identity.getSignInClient(requireActivity())
//        oneTapClient.signOut().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.i("Google Signout", "Signed out Google successfully")
//            } else {
//                Log.e("Google Signout", "Google sign-out failed", task.exception)
//            }
//        }
//
//        // Always sign out from AWS Cognito
//        Amplify.Auth.signOut { signOutResult ->
//            requireActivity().runOnUiThread {
//                when (signOutResult) {
//                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
//                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
//                        Log.i("AuthQuickStart", "Signed out successfully")
//                    }
//                    is AWSCognitoAuthSignOutResult.PartialSignOut -> {
//                        Log.e("AuthQuickStart", "Partial sign-out, hostedUIError: ${signOutResult.hostedUIError}")
//                        Log.e("AuthQuickStart", "Partial sign-out, globalSignOutError: ${signOutResult.globalSignOutError}")
//                        Log.e("AuthQuickStart", "Partial sign-out, revokeTokenError: ${signOutResult.revokeTokenError}")
//                    }
//                    is AWSCognitoAuthSignOutResult.FailedSignOut -> {
//                        Toast.makeText(context, "Sign out failed: ${signOutResult.exception.message}", Toast.LENGTH_SHORT).show()
//                        Log.e("AuthQuickStart", "Sign out Failed", signOutResult.exception)
//                    }
//                }
//            }
//        }
//    }
}



// OLD VERSIONS:--------------------------------------------------------------------------------------------


// V1 - facebook login no seperation
//class LoginFragment : Fragment(){
//
//    private lateinit var fbcallbackManager: CallbackManager
//    private lateinit var facebooklogin: LoginButton
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        fbcallbackManager = CallbackManager.Factory.create()
//        facebooklogin = view.findViewById(R.id.btn_fb)
//        facebooklogin.setPermissions("email")
//        facebooklogin.setFragment(this)
//
//        facebooklogin.registerCallback(fbcallbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(result: LoginResult) {
//                Log.d("LoginFragment", "Facebook login successful")
//                handleFacebookAccessToken(result.accessToken)
//            }
//            override fun onCancel() {
//                Log.d("LoginFragment", "Facebook login cancelled")
//            }
//            override fun onError(error: FacebookException) {
//                Log.e("LoginFragment", "Facebook login error", error)
//            }
//        })
//    }
//
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        Log.d("LoginFragment", "Handling Facebook access token")
//        Amplify.Auth.signInWithSocialWebUI(
//            AuthProvider.facebook(),
//            requireActivity(),
//            { result ->
//                requireActivity().runOnUiThread {
//                    Log.d("LoginFragment", "Sign-in result: ${result.isSignedIn}")
//                    if (result.isSignedIn) {
//                        // Successfully signed in
//                        Log.d("LoginFragment", "User is signed in with Facebook")
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                    } else {
//                        Log.w("LoginFragment", "Sign-in result was not signed in")
//                    }
//                }
//            },
//            { error ->
//                requireActivity().runOnUiThread {
//                    Log.e("AmplifyQuickstart", "Sign-in failed", error)
//                }
//            }
//        )
//    }
//
//
//}


// V2 - facebook login using facebook handler
//class LoginFragment : Fragment(){
//
//    private lateinit var facebookLoginHandler: FacebookLoginHandler
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        facebookLoginHandler = FacebookLoginHandler(this) { isSuccessful ->
//            if (isSuccessful) {
//                Log.d("LoginFragment", "User is signed in with Facebook")
//                Toast.makeText(context, "Signed in using Facebook", Toast.LENGTH_SHORT).show()
//                storeSignInMethod(SignInMethod.FACEBOOK)
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            } else {
//                Log.w("LoginFragment", "Sign-in was not successful")
//            }
//        }
//        facebookLoginHandler.initialize(view)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        facebookLoginHandler.onActivityResult(requestCode, resultCode, data)
//    }
//
//    // Store the login type locally to reference
//    private fun storeSignInMethod(method: SignInMethod) {
//        val sharedPref = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        with(sharedPref.edit()) {
//            putString("currentSignInMethod", method.name)
//            apply()
//        }
//    }
//}



// V3 - Google sign in
//class LoginFragment : Fragment() {
//
//    private lateinit var googleSignInClient: GoogleSignInClient
//
//    companion object {
//        private const val RC_SIGN_IN = 9001
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_login, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Configure Google Sign-In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .requestIdToken(getString(R.string.google_app_id))
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//        view.findViewById<View>(R.id.btn_google).setOnClickListener {
//            signIn()
//        }
//    }
//
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(com.amplifyframework.api.ApiException::class.java)
//            // Signed in successfully
//            Toast.makeText(context, "Signed in using Google", Toast.LENGTH_SHORT).show()
//            storeSignInMethod(SignInMethod.GOOGLE)
//            Log.d("LoginFragment", "Google sign-in successful")
//            handleGoogleSignInAccount(account)
//        } catch (e: com.amplifyframework.api.ApiException) {
//            // Sign-in failed
//            Log.e("LoginFragment", "Google sign-in failed", e)
//        }
//    }
//
//    private fun handleGoogleSignInAccount(account: GoogleSignInAccount?) {
//        Log.d("LoginFragment", "Handling Google sign-in account")
//        account?.let { googleAccount ->
//            val idToken = googleAccount.idToken
//            if (idToken != null) {
//                // Use Amplify to federate with Cognito
//                Amplify.Auth.signInWithSocialWebUI(
//                    AuthProvider.google(),
//                    requireActivity(),
//                    { result ->
//                        Log.i("AuthQuickStart", "Federated google Successfully: $result")
//                        // User is now signed in to your Cognito User Pool
//                        requireActivity().runOnUiThread {
//                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//                        }
//                    },
//                    { error ->
//                        Log.e("AuthQuickStart", "Federation failed", error)
//                    }
//                )
//            } else {
//                Log.e("LoginFragment", "Google Sign-In successful, but no ID token available")
//            }
//        }
//    }
//
//
//    // Store the login type locally to reference
//    private fun storeSignInMethod(method: SignInMethod) {
//        val sharedPref = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        with(sharedPref.edit()) {
//            putString("currentSignInMethod", method.name)
//            apply()
//        }
//    }
//}

