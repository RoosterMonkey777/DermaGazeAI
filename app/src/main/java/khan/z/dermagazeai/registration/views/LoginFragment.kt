package khan.z.dermagazeai.registration.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.SignInMethod
import khan.z.dermagazeai.registration.helpers.FacebookLoginHandler

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

class LoginFragment : Fragment(){

    private lateinit var facebookLoginHandler: FacebookLoginHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        facebookLoginHandler = FacebookLoginHandler(this) { isSuccessful ->
            if (isSuccessful) {
                Log.d("LoginFragment", "User is signed in with Facebook")
                Toast.makeText(context, "Signed in using Facebook", Toast.LENGTH_SHORT).show()
                storeSignInMethod(SignInMethod.FACEBOOK)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Log.w("LoginFragment", "Sign-in was not successful")
            }
        }
        facebookLoginHandler.initialize(view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookLoginHandler.onActivityResult(requestCode, resultCode, data)
    }

    // Store the login type locally to reference
    private fun storeSignInMethod(method: SignInMethod) {
        val sharedPref = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("currentSignInMethod", method.name)
            apply()
        }
    }

}