package khan.z.dermagazeai.registration.helpers

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.SignInMethod

class GoogleSignInHandler(private val fragment: Fragment, private val navController: NavController) {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInButton: SignInButton

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    fun initializeGoogleSignIn(view: View, googleAppId: String, signInButtonId: Int, customButtonId: Int) {
        signInButton = view.findViewById(signInButtonId)
        val customGoogleButton: ImageButton = view.findViewById(customButtonId)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(googleAppId)
            .build()

        googleSignInClient = GoogleSignIn.getClient(fragment.requireActivity(), gso)

        customGoogleButton.setOnClickListener {
            signIn()
        }
    }

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Toast.makeText(fragment.context, "Signed in using Google", Toast.LENGTH_SHORT).show()
            AuthorizationUtils.storeSignInMethod(fragment.requireActivity(), SignInMethod.GOOGLE)
            Log.d("GoogleSignInHandler", "Google sign-in successful")
            handleGoogleSignInAccount(account)
        } catch (e: ApiException) {
            Log.e("GoogleSignInHandler", "Google sign-in failed", e)
            handleSignInFailure(e)
        }
    }

    private fun handleSignInFailure(e: ApiException) {
        when (e.statusCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> {
                Toast.makeText(fragment.context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(fragment.context, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleGoogleSignInAccount(account: GoogleSignInAccount?) {
        Log.d("GoogleSignInHandler", "Handling Google sign-in account")
        account?.let { googleAccount ->
            val idToken = googleAccount.idToken
            if (idToken != null) {
                Amplify.Auth.signInWithSocialWebUI(
                    AuthProvider.google(),
                    fragment.requireActivity(),
                    { result ->
                        Log.i("AuthQuickStart", "Federated google Successfully: $result")
                        fragment.requireActivity().runOnUiThread {
                            navController.navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    },
                    { error ->
                        Log.e("AuthQuickStart", "Federation failed", error)
                    }
                )
            } else {
                Log.e("GoogleSignInHandler", "Google Sign-In successful, but no ID token available")
            }
        }
    }
}
