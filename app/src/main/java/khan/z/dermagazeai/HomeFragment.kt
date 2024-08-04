package khan.z.dermagazeai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.core.Amplify
import com.facebook.login.LoginManager


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        Log.d("LoginFragment", "Sign out initiated")

        // signout from facebook
        LoginManager.getInstance().logOut()

        // signout from aws-cognito
        Amplify.Auth.signOut { signOutResult ->
            requireActivity().runOnUiThread {
                when (signOutResult) {
                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        Log.i("AuthQuickStart", "Signed out successfully")
                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
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