package khan.z.dermagazeai.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import khan.z.dermagazeai.R

class LoginFragment : Fragment(){

    private lateinit var callbackManager: CallbackManager
    private lateinit var fbloginButton: LoginButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
            //signOut()
        }

        callbackManager = CallbackManager.Factory.create()
        fbloginButton = view.findViewById(R.id.btn_fb)
        fbloginButton.setPermissions("email")
        fbloginButton.setFragment(this)

        fbloginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.d("LoginFragment", "Facebook login successful")
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Log.d("LoginFragment", "Facebook login cancelled")
            }

            override fun onError(error: FacebookException) {
                Log.e("LoginFragment", "Facebook login error", error)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("LoginFragment", "Handling Facebook access token")
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.facebook(),
            requireActivity(),
            { result ->
                requireActivity().runOnUiThread {
                    Log.d("LoginFragment", "Sign-in result: ${result.isSignedIn}")
                    if (result.isSignedIn) {
                        // Successfully signed in
                        Log.d("LoginFragment", "User is signed in with Facebook")
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Log.w("LoginFragment", "Sign-in result was not signed in")
                    }
                }
            },
            { error ->
                requireActivity().runOnUiThread {
                    Log.e("AmplifyQuickstart", "Sign-in failed", error)
                }
            }
        )
    }

    private fun signOut() {
        Log.d("LoginFragment", "Sign out initiated")
        Amplify.Auth.signOut { signOutResult ->
            requireActivity().runOnUiThread {
                when (signOutResult) {
                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        Log.i("AuthQuickStart", "Signed out successfully")
                    }
                    is AWSCognitoAuthSignOutResult.PartialSignOut -> {
                        Log.e("AuthQuickStart", "Partial sign-out, hostedUIError: ${signOutResult.hostedUIError}")
                        Log.e("AuthQuickStart", "Partial sign-out, globalSignOutError: ${signOutResult.globalSignOutError}")
                        Log.e("AuthQuickStart", "Partial sign-out, revokeTokenError: ${signOutResult.revokeTokenError}")
                    }
                    is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                        Toast.makeText(context, "Sign out failed: ${signOutResult.exception.message}", Toast.LENGTH_SHORT).show()
                        Log.e("AuthQuickStart", "Sign out Failed", signOutResult.exception)
                    }
                }
            }
        }
    }
}