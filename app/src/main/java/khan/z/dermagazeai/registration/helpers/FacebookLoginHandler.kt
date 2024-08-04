package khan.z.dermagazeai.registration.helpers

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import khan.z.dermagazeai.R

class FacebookLoginHandler(private val fragment: Fragment, private val onLoginResult: (Boolean) -> Unit) {

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton

    fun initialize(view: View) {
        callbackManager = CallbackManager.Factory.create()
        loginButton = view.findViewById(R.id.btn_fb)
        loginButton.setPermissions("email")
        loginButton.setFragment(fragment)

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("FacebookLoginHandler", "Facebook login successful")
                handleFacebookAccessToken(result.accessToken)
            }
            override fun onCancel() {
                Log.d("FacebookLoginHandler", "Facebook login cancelled")
                onLoginResult(false)
            }
            override fun onError(error: FacebookException) {
                Log.e("FacebookLoginHandler", "Facebook login error", error)
                onLoginResult(false)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("FacebookLoginHandler", "Handling Facebook access token")
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.facebook(),
            fragment.requireActivity(),
            { result ->
                fragment.requireActivity().runOnUiThread {
                    Log.d("FacebookLoginHandler", "Sign-in result: ${result.isSignedIn}")
                    onLoginResult(result.isSignedIn)
                }
            },
            { error ->
                fragment.requireActivity().runOnUiThread {
                    Log.e("AmplifyQuickstart", "Sign-in failed", error)
                    onLoginResult(false)
                }
            }
        )
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}