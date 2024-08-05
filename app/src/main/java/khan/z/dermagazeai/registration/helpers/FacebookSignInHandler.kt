package khan.z.dermagazeai.registration.helpers

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import khan.z.dermagazeai.registration.model.SignInMethod

class FacebookSignInHandler(private val fragment: Fragment, private val navController: NavController, private val destinationFragmentId: Int) {

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private lateinit var loginButton: LoginButton
    private lateinit var customFacebookButton: ImageButton



    init {
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("FacebookSignInHandler", "Facebook login successful")
                Toast.makeText(fragment.context, "Signed in using Facebook", Toast.LENGTH_SHORT).show()
                AuthorizationUtils.storeSignInMethod(fragment.requireActivity(), SignInMethod.FACEBOOK)
                handleFacebookAccessToken(result.accessToken)
            }
            override fun onCancel() {
                Log.d("FacebookSignInHandler", "Facebook login cancelled")
            }
            override fun onError(error: FacebookException) {
                Log.e("FacebookSignInHandler", "Facebook login error", error)
            }
        })
    }

    fun initializeFacebookLogin(view: View, loginButtonId: Int, customButtonId: Int) {
        loginButton = view.findViewById(loginButtonId)
        customFacebookButton = view.findViewById(customButtonId)

        loginButton.setPermissions("email")
        loginButton.setFragment(fragment)

        customFacebookButton.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(fragment, listOf("email"))
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("FacebookSignInHandler", "Handling Facebook access token")
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.facebook(),
            fragment.requireActivity(),
            { result ->
                fragment.requireActivity().runOnUiThread {
                    Log.d("FacebookSignInHandler", "Sign-in result: ${result.isSignedIn}")
                    if (result.isSignedIn) {
                        // Successfully signed in
                        Log.d("FacebookSignInHandler", "User is signed in with Facebook")
                        //navController.navigate(R.id.action_loginFragment_to_homeFragment)
                        navController.navigate(destinationFragmentId)
                    } else {
                        Log.w("FacebookSignInHandler", "Sign-in result was not signed in")
                    }
                }
            },
            { error ->
                fragment.requireActivity().runOnUiThread {
                    Log.e("AmplifyQuickstart", "Sign-in failed", error)
                }
            }
        )
    }
}

