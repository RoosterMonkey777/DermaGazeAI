package khan.z.dermagazeai

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.manager.TopMenuManager
import khan.z.dermagazeai.manager.UserProfileManager


// CURRENT VERSION ----------------------------------------------------
class HomeFragment : Fragment() {

    private val userProfileManager = UserProfileManager()
    private lateinit var topMenuManager: TopMenuManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "onViewCreated called")

        // Initialize the MenuManager
        topMenuManager = TopMenuManager(requireContext(), this) { from, to ->
            rotateButton(view.findViewById(R.id.menu_button), from, to)
        }

        // Setup the menu button
        val menuButton: ImageButton = view.findViewById(R.id.menu_button)
        topMenuManager.setupMenuButton(menuButton)

        // Find the user profile in database
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
    }

    private fun rotateButton(button: ImageButton, from: Float, to: Float) {
        val rotateAnimator = ObjectAnimator.ofFloat(button, "rotation", from, to)
        rotateAnimator.duration = 150
        rotateAnimator.start()
    }

    private fun navigateToUserProfileDialog() {
        requireActivity().runOnUiThread {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileDialogFragment)
        }
    }
}

//class HomeFragment : Fragment() {
//
//    private var isRotated = false
//    private val userProfileManager = UserProfileManager()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Log.d("HomeFragment", "onViewCreated called")
//
//        // top left menu button
//        val menuButton: ImageButton = view.findViewById(R.id.menu_button)
//
//        menuButton.setOnClickListener {
//            if (!isRotated) {
//                rotateButton(menuButton, 0f, 90f)
//                showPopupMenu(menuButton)
//            } else {
//                rotateButton(menuButton, 90f, 0f)
//            }
//        }
//
//
//
//        // find the user profile in database
//        userProfileManager.fetchUserEmail(
//            onSuccess = { email ->
//                Log.d("HomeFragment", "User email: $email")
//                userProfileManager.checkUserProfile(
//                    email,
//                    onProfileFound = { user ->
//                        Log.d("HomeFragment", "User profile found: $user")
//                        // Handle profile found scenario
//                    },
//                    onProfileNotFound = {
//                        Log.d("HomeFragment", "No user profile found, navigating to UserProfileDialogFragment")
//                        navigateToUserProfileDialog()
//                    },
//                    onError = { error ->
//                        Log.e("HomeFragment", "Failed to query user profile", error)
//                    }
//                )
//            },
//            onError = { error ->
//                Log.e("HomeFragment", "Failed to fetch user email", error)
//            }
//        )
//
//    }
//
//    private fun rotateButton(button: ImageButton, from: Float, to: Float) {
//        val rotateAnimator = ObjectAnimator.ofFloat(button, "rotation", from, to)
//        rotateAnimator.duration = 150
//        rotateAnimator.start()
//    }
//
//    private fun showPopupMenu(view: View) {
//        val popup = PopupMenu(requireContext(), view)
//        popup.menuInflater.inflate(R.menu.top_menu_button, popup.menu)
//
//        popup.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.action_faq -> {
//                    //findNavController().navigate(R.id.action_homeFragment_to_faqFragment)
//                    true
//                }
//                R.id.action_about -> {
//                    //findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
//                    true
//                }
//                R.id.action_signout -> {
//                    AuthorizationUtils.signOut(requireContext()) {
//                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popup.setOnDismissListener {
//            if (isRotated) {
//                rotateButton(view as ImageButton, 90f, 0f)
//                isRotated = false
//            }
//        }
//
//        popup.show()
//
//        isRotated = true
//    }
//
//
//
//    private fun navigateToUserProfileDialog() {
//        requireActivity().runOnUiThread {
//            findNavController().navigate(R.id.action_homeFragment_to_userProfileDialogFragment)
//        }
//    }
//}









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