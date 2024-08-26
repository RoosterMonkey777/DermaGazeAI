package khan.z.dermagazeai

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import khan.z.dermagazeai.manager.UserProfileManager
import khan.z.dermagazeai.registration.helpers.AuthorizationUtils


// CURRENT VERSION ----------------------------------------------------
class HomeFragment : Fragment() {

    private var isRotated = false
    private val userProfileManager = UserProfileManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "onViewCreated called")

        // top left menu button
        val menuButton: ImageButton = view.findViewById(R.id.menu_button)

        menuButton.setOnClickListener {
            val rotateAnimator = if (isRotated) {
                ObjectAnimator.ofFloat(menuButton, "rotation", 90f, 0f) // Rotate back to 0 degrees
            } else {
                ObjectAnimator.ofFloat(menuButton, "rotation", 0f, 90f) // Rotate to 90 degrees
            }
            rotateAnimator.duration = 150
            rotateAnimator.start()

            isRotated = !isRotated // Toggle the rotation state

            // Optionally, show the menu here only when rotating to 90 degrees
            if (isRotated) {
                //showPopupMenu(menuButton)
            }
        }

        // find the user profile in database
        userProfileManager.fetchUserEmail(
            onSuccess = { email ->
                Log.d("HomeFragment", "User email: $email")
                userProfileManager.checkUserProfile(
                    email,
                    onProfileFound = { user ->
                        Log.d("HomeFragment", "User profile found: $user")
                        // Handle profile found scenario
                    },
                    onProfileNotFound = {
                        Log.d("HomeFragment", "No user profile found, navigating to UserProfileDialogFragment")
                        navigateToUserProfileDialog()
                    },
                    onError = { error ->
                        Log.e("HomeFragment", "Failed to query user profile", error)
                    }
                )
            },
            onError = { error ->
                Log.e("HomeFragment", "Failed to fetch user email", error)
            }
        )

        view.findViewById<Button>(R.id.btn_signout).setOnClickListener {
            Log.d("HomeFragment", "Sign out button clicked")
            AuthorizationUtils.signOut(requireContext()) {
                Log.d("HomeFragment", "User signed out, navigating to login screen")
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

//    private fun showPopupMenu(view: View) {
//        val popup = PopupMenu(requireContext(), view)
//        popup.menuInflater.inflate(R.menu.menu_main, popup.menu)
//        popup.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.action_faq -> {
//                    // Handle FAQ action
//                    true
//                }
//                R.id.action_about -> {
//                    // Handle About action
//                    true
//                }
//                R.id.action_signout -> {
//                    // Handle Sign Out action
//                    AuthorizationUtils.signOut(requireContext()) {
//                        Log.d("HomeFragment", "User signed out, navigating to login screen")
//                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
//        popup.show()
//    }



    private fun navigateToUserProfileDialog() {
        requireActivity().runOnUiThread {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileDialogFragment)
        }
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