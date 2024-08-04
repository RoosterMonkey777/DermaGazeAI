package khan.z.dermagazeai.registration.helpers

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.Identity
import khan.z.dermagazeai.registration.SignInMethod

/**
 * Utility class for authentication-related functions.
 */
object AuthorizationUtils {

    fun signOut(context: Context, onSignOutComplete: () -> Unit) {
        Log.d("AuthUtils", "Sign out initiated")

        val currentSignInMethod = getSignInMethod(context)
        when (currentSignInMethod) {
            SignInMethod.FACEBOOK -> {
                LoginManager.getInstance().logOut()
                Log.i("Facebook Signout", "Signed out Facebook successfully")
            }
            SignInMethod.GOOGLE -> {
                val oneTapClient = Identity.getSignInClient(context as Activity)
                oneTapClient.signOut().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("Google Signout", "Signed out Google successfully")
                    } else {
                        Log.e("Google Signout", "Google sign-out failed", task.exception)
                    }
                }
            }
            SignInMethod.NONE -> {
                Log.w("AuthUtils", "No sign-in method recorded")
            }
        }

        // Always sign out from AWS Cognito
        Amplify.Auth.signOut { signOutResult ->
            (context as Activity).runOnUiThread {
                clearSignInMethod(context)
                when (signOutResult) {
                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        Log.i("AuthQuickStart", "Signed out successfully")
                        onSignOutComplete()
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

    fun storeSignInMethod(context: Context, method: SignInMethod) {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("currentSignInMethod", method.name)
            apply()
        }
    }

    private fun getSignInMethod(context: Context): SignInMethod {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val methodName = sharedPref.getString("currentSignInMethod", SignInMethod.NONE.name)
        return SignInMethod.valueOf(methodName ?: SignInMethod.NONE.name)
    }

    private fun clearSignInMethod(context: Context) {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("currentSignInMethod", SignInMethod.NONE.name)
            apply()
        }
    }


}
