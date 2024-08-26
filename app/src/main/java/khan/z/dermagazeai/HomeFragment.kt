package khan.z.dermagazeai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import khan.z.dermagazeai.registration.helpers.AuthorizationUtils


// CURRENT VERSION ----------------------------------------------------
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

        Log.d("HomeFragment", "onViewCreated called")

        // Fetch the current user's email
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                Log.d("HomeFragment", "User attributes fetched successfully")
                val email = attributes.firstOrNull { it.key.keyString == "email" }?.value
                if (email != null) {
                    Log.d("HomeFragment", "User email: $email")
                    checkUserProfile(email)
                } else {
                    Log.e("HomeFragment", "Email not found in user attributes")
                }
            },
            { error -> Log.e("HomeFragment", "Failed to fetch user attributes", error) }
        )

        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
            Log.d("HomeFragment", "Sign out button clicked")
            AuthorizationUtils.signOut(requireContext()) {
                Log.d("HomeFragment", "User signed out, navigating to login screen")
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

    private fun checkUserProfile(email: String) {
        Log.d("HomeFragment", "Checking user profile for email: $email")
        // Use the generated query method for fetching user by email
        Amplify.API.query(
            ModelQuery.list(User::class.java, User.EMAIL.eq(email)),
            { response ->
                val items = response.data?.items
                if (items == null || !items.iterator().hasNext()) {
                    Log.d("HomeFragment", "No user profile found, navigating to UserProfileDialogFragment")
                    requireActivity().runOnUiThread {
                        findNavController().navigate(R.id.action_homeFragment_to_userProfileDialogFragment)
                    }
                } else {
                    Log.d("HomeFragment", "User profile found: ${items.firstOrNull()}")
                }
            },
            { error -> Log.e("HomeFragment", "Query failed", error) }
        )
    }
}








// OLD VERSIONS ----------------------------------------------------

//class HomeFragment : Fragment() {
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
//            signOut()
//        }
//    }
//
//    //TODO: Will have to move this function to make it cleaner later
//    private fun signOut() {
//        Log.d("LoginFragment", "Sign out initiated")
//
//        val currentSignInMethod = getSignInMethod()
//        when (currentSignInMethod) {
//            SignInMethod.FACEBOOK -> {
//                LoginManager.getInstance().logOut()
//                Log.i("Facebook Signout", "Signed out facebook successfully")
//            }
//            SignInMethod.GOOGLE -> {
//                val oneTapClient = Identity.getSignInClient(requireActivity())
//                oneTapClient.signOut().addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.i("Google Signout", "Signed out Google successfully")
//                    } else {
//                        Log.e("Google Signout", "Google sign-out failed", task.exception)
//                    }
//                }
//            }
//            SignInMethod.NONE -> { Log.w("HomeFragment", "No sign-in method recorded") }
//        }
//
//        // Always sign out from AWS Cognito
//        Amplify.Auth.signOut { signOutResult ->
//            requireActivity().runOnUiThread {
//                clearSignInMethod()
//                when (signOutResult) {
//                    is AWSCognitoAuthSignOutResult.CompleteSignOut -> {
//                        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
//                        Log.i("AuthQuickStart", "Signed out successfully")
//                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
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
//
//    private fun getSignInMethod(): SignInMethod {
//        val sharedPref = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        val methodName = sharedPref.getString("currentSignInMethod", SignInMethod.NONE.name)
//        return SignInMethod.valueOf(methodName ?: SignInMethod.NONE.name)
//    }
//
//    private fun clearSignInMethod() {
//        val sharedPref = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        with(sharedPref.edit()) {
//            putString("currentSignInMethod", SignInMethod.NONE.name)
//            apply()
//        }
//    }
//}