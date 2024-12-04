package khan.z.dermagazeai

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.SkinAssessment
import com.amplifyframework.datastore.generated.model.SkinCareProduct
import khan.z.dermagazeai.machinelearning.views.SkinAssessmentAdapter
import khan.z.dermagazeai.manager.BannerAdapter
import khan.z.dermagazeai.manager.TopMenuManager
import khan.z.dermagazeai.manager.UserProfileManager
import khan.z.dermagazeai.medication.views.RecommendationPagerAdapter
import khan.z.dermagazeai.registration.views.NavigationViewModel
import java.util.Calendar



class HomeFragment : Fragment() {

    private val userProfileManager = UserProfileManager()
    private lateinit var topMenuManager: TopMenuManager
    private lateinit var profileImageView: ImageView
    private lateinit var greetingTextView: TextView
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var recommendationPagerAdapter: RecommendationPagerAdapter
    private lateinit var assessmentRecyclerView: RecyclerView
    private lateinit var skinAssessmentAdapter: SkinAssessmentAdapter
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var bannerProgressBar: ProgressBar
    private lateinit var recommendationProgressBar: ProgressBar
    private lateinit var assessmentProgressBar: ProgressBar
    private lateinit var bannerViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "onViewCreated called")

        // Ensure the user is authenticated before loading the home screen
        checkAuthenticationStatus()

        navigationViewModel = ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)

        // Initialize UI elements
        profileImageView = view.findViewById(R.id.profile_image)
        greetingTextView = view.findViewById(R.id.greeting_message)
        viewPager = view.findViewById(R.id.viewPager_recommendations)
        assessmentRecyclerView = view.findViewById(R.id.recyclerView_assessmentHistory)
        assessmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bannerViewPager = view.findViewById(R.id.viewPagerSlider)
        bannerProgressBar = view.findViewById(R.id.progressBarBanner)
        recommendationProgressBar = view.findViewById(R.id.progressBarRecommendations)
        assessmentProgressBar = view.findViewById(R.id.progressBarAssessmentHistory)

        setupBanner()

        // Check the flag and reset it
        val showGreeting = navigationViewModel.fromLogin
        navigationViewModel.fromLogin = false

        // Initialize the MenuManager
        topMenuManager = TopMenuManager(requireContext(), this) { from, to ->
            rotateButton(view.findViewById(R.id.menu_button), from, to)
        }

        // Setup the menu button
        val menuButton: ImageButton = view.findViewById(R.id.menu_button)
        topMenuManager.setupMenuButton(menuButton)

        // Check user profile and load data
        userProfileManager.fetchUserEmail(
            onSuccess = { email ->
                Log.d("HomeFragment", "User email: $email")
                userProfileManager.checkUserProfile(
                    email,
                    onProfileFound = { user ->
                        Log.d("HomeFragment", "User profile found: $user")
                        requireActivity().runOnUiThread {
                            if (showGreeting) {
                                setDynamicGreeting(user.firstname ?: "")
                            } else {
                                greetingTextView.text = user.firstname ?: ""
                            }
                        }
                        // Trigger Lambda and load product recommendations
                        triggerLambdaFunctionAndFetchProducts(user.id)
                        // Fetch and display Skin Assessments in RecyclerView
                        fetchSkinAssessments(user.id) { assessments ->
                            requireActivity().runOnUiThread {
                                assessmentProgressBar.visibility = View.GONE // Hide progress bar
                                skinAssessmentAdapter = SkinAssessmentAdapter(assessments)
                                assessmentRecyclerView.adapter = skinAssessmentAdapter
                            }
                        }
                    },
                    onProfileNotFound = {
                        Log.d("HomeFragment", "No user profile found")
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

    private fun checkAuthenticationStatus() {
        Amplify.Auth.fetchAuthSession(
            { session ->
                if (!session.isSignedIn) {
                    requireActivity().runOnUiThread {
                        Log.w("HomeFragment", "User is not authenticated. Redirecting to login.")
                        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    }
                }
            },
            { error ->
                Log.e("HomeFragment", "Failed to fetch authentication session: ${error.message}")
            }
        )
    }

    private fun setupBanner() {
        val bannerImages = listOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
        )

        // Initialize the BannerAdapter
        bannerAdapter = BannerAdapter(bannerImages)
        bannerViewPager.adapter = bannerAdapter

        // Hide the progress bar when the banner images are set
        bannerProgressBar.visibility = View.GONE
    }

    private fun setDynamicGreeting(firstName: String) {
        val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 6..11 -> "Good Morning, $firstName"
            in 12..17 -> "Good Afternoon, $firstName"
            else -> "Good Evening, $firstName"
        }

        requireActivity().runOnUiThread {
            greetingTextView.text = greeting
            Handler(Looper.getMainLooper()).postDelayed({
                fadeOutGreeting(firstName)
            }, 3000)
        }
    }

    private fun fadeOutGreeting(firstName: String) {
        requireActivity().runOnUiThread {
            greetingTextView.animate()
                .alpha(0f)
                .setDuration(1000)
                .withEndAction {
                    greetingTextView.text = firstName
                    greetingTextView.animate().alpha(1f).duration = 1000
                }
        }
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

    private fun triggerLambdaFunctionAndFetchProducts(userId: String) {
        userProfileManager.triggerLambdaFunction(
            userId = userId,
            onSuccess = { recommendedProductNames ->
                fetchRecommendedProducts(recommendedProductNames) { products ->
                    requireActivity().runOnUiThread {
                        recommendationProgressBar.visibility = View.GONE
                        setupViewPager(products)
                    }
                }
            },
            onError = { error ->
                Log.e("HomeFragment", "Error triggering Lambda: ${error.message}")
            }
        )
    }

    private fun fetchRecommendedProducts(productNames: List<String>, onProductsFetched: (List<SkinCareProduct>) -> Unit) {
        val fetchedProducts = mutableListOf<SkinCareProduct>()
        val totalProducts = productNames.size
        var productsFetched = 0

        productNames.forEach { productName ->
            Amplify.API.query(
                ModelQuery.list(SkinCareProduct::class.java, SkinCareProduct.PRODUCT_NAME.eq(productName)),
                { response ->
                    val products = response.data?.items?.filterNotNull()
                    if (products != null && products.isNotEmpty()) {
                        fetchedProducts.addAll(products)
                        Log.d("HomeFragment", "Fetched ${products.size} products for $productName")
                    }
                    productsFetched++

                    if (productsFetched == totalProducts) {
                        onProductsFetched(fetchedProducts)
                    }
                },
                { error ->
                    Log.e("HomeFragment", "Error fetching product $productName: ${error.message}")
                    productsFetched++
                    if (productsFetched == totalProducts) {
                        onProductsFetched(fetchedProducts)
                    }
                }
            )
        }
    }

    private fun setupViewPager(products: List<SkinCareProduct>) {
        if (products.isEmpty()) {
            Log.e("HomeFragment", "No products available to display.")
            return
        }

        recommendationPagerAdapter = RecommendationPagerAdapter(products)
        viewPager.adapter = recommendationPagerAdapter
    }

    private fun fetchSkinAssessments(userId: String, onAssessmentsFetched: (List<SkinAssessment>) -> Unit) {
        Amplify.API.query(
            ModelQuery.list(SkinAssessment::class.java, SkinAssessment.USER_PROFILE_ID.eq(userId)),
            { response ->
                val assessments = response.data?.items?.filterNotNull() ?: emptyList()
                onAssessmentsFetched(assessments)
            },
            { error ->
                Log.e("HomeFragment", "Failed to fetch assessments: ${error.message}")
            }
        )
    }
}



//class HomeFragment : Fragment() {
//
//    private val userProfileManager = UserProfileManager()
//    private lateinit var topMenuManager: TopMenuManager
//    private lateinit var profileImageView: ImageView
//    private lateinit var greetingTextView: TextView
//    private lateinit var navigationViewModel: NavigationViewModel
//    private lateinit var viewPager: ViewPager2
//    private lateinit var recommendationPagerAdapter: RecommendationPagerAdapter
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
//        navigationViewModel = ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)
//
//        // Initialize UI elements
//        profileImageView = view.findViewById(R.id.profile_image)
//        greetingTextView = view.findViewById(R.id.greeting_message)
//        viewPager = view.findViewById(R.id.viewPager_recommendations)
//
//        // Check the flag and reset it
//        val showGreeting = navigationViewModel.fromLogin
//        navigationViewModel.fromLogin = false
//
//        // Initialize the MenuManager
//        topMenuManager = TopMenuManager(requireContext(), this) { from, to ->
//            rotateButton(view.findViewById(R.id.menu_button), from, to)
//        }
//
//        // Setup the menu button
//        val menuButton: ImageButton = view.findViewById(R.id.menu_button)
//        topMenuManager.setupMenuButton(menuButton)
//
//        // Check if the user needs to see the ToS dialog or User Profile dialog
//        userProfileManager.fetchUserEmail(
//            onSuccess = { email ->
//                Log.d("HomeFragment", "User email: $email")
//                userProfileManager.checkUserProfile(
//                    email,
//                    onProfileFound = { user ->
//                        Log.d("HomeFragment", "User profile found: $user")
//                        requireActivity().runOnUiThread {
//                            if (showGreeting) {
//                                setDynamicGreeting(user.firstname ?: "")
//                            } else {
//                                greetingTextView.text = user.firstname ?: ""
//                            }
//                        }
//                        // Trigger Lambda and load product recommendations
//                        triggerLambdaFunctionAndFetchProducts(user.id)
//                    },
//                    onProfileNotFound = {
//                        Log.d("HomeFragment", "No user profile found")
//                        if (shouldShowTermsOfService()) {
//                            navigateToTermsOfServiceDialog()
//                        } else {
//                            navigateToUserProfileDialog()
//                        }
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
//    }
//
//    private fun shouldShowGreeting(): Boolean {
//        val previousBackStackEntry = findNavController().previousBackStackEntry
//        val previousDestination = previousBackStackEntry?.destination
//
//        // Check if the previous destination was the LoginFragment
//        return previousDestination?.id == R.id.loginFragment
//    }
//
//    private fun setDynamicGreeting(firstName: String) {
//        val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
//            in 6..11 -> "Good Morning, $firstName"
//            in 12..17 -> "Good Afternoon, $firstName"
//            else -> "Good Evening, $firstName"
//        }
//
//        // Ensure the UI update is performed on the main thread
//        requireActivity().runOnUiThread {
//            greetingTextView.text = greeting
//
//            // Start the fade-out animation after 3 seconds
//            Handler(Looper.getMainLooper()).postDelayed({
//                fadeOutGreeting(firstName)
//            }, 3000)
//        }
//    }
//
//    private fun fadeOutGreeting(firstName: String) {
//        requireActivity().runOnUiThread {
//            greetingTextView.animate()
//                .alpha(0f)
//                .setDuration(1000)
//                .withEndAction {
//                    // After fade-out, set the TextView to show just the user's name
//                    greetingTextView.text = firstName
//                    greetingTextView.animate().alpha(1f).duration = 1000
//                }
//        }
//    }
//
//    private fun rotateButton(button: ImageButton, from: Float, to: Float) {
//        val rotateAnimator = ObjectAnimator.ofFloat(button, "rotation", from, to)
//        rotateAnimator.duration = 150
//        rotateAnimator.start()
//    }
//
//    private fun navigateToUserProfileDialog() {
//        requireActivity().runOnUiThread {
//            findNavController().navigate(R.id.action_homeFragment_to_userProfileDialogFragment)
//        }
//    }
//
//    private fun shouldShowTermsOfService(): Boolean {
//        val sharedPref = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
//        return !sharedPref.getBoolean("consentGiven", false)
//    }
//
//    private fun navigateToTermsOfServiceDialog() {
//        requireActivity().runOnUiThread {
//            findNavController().navigate(R.id.action_homeFragment_to_termsOfServiceDialogFragment)
//        }
//    }
//
//    private fun triggerLambdaFunctionAndFetchProducts(userId: String) {
//        userProfileManager.triggerLambdaFunction(
//            userId = userId,
//            onSuccess = { recommendedProductNames ->
//                fetchRecommendedProducts(recommendedProductNames) { products ->
//                    // Switch to the main thread before setting up the ViewPager
//                    requireActivity().runOnUiThread {
//                        setupViewPager(products)
//                    }
//                }
//            },
//            onError = { error ->
//                Log.e("HomeFragment", "Error triggering Lambda: ${error.message}")
//            }
//        )
//    }
//
//    private fun fetchRecommendedProducts(productNames: List<String>, onProductsFetched: (List<SkinCareProduct>) -> Unit) {
//        val fetchedProducts = mutableListOf<SkinCareProduct>()
//        val totalProducts = productNames.size
//        var productsFetched = 0
//
//        productNames.forEach { productName ->
//            Amplify.API.query(
//                ModelQuery.list(SkinCareProduct::class.java, SkinCareProduct.PRODUCT_NAME.eq(productName)),
//                { response ->
//                    val products = response.data?.items?.filterNotNull()
//                    if (products != null && products.isNotEmpty()) {
//                        fetchedProducts.addAll(products)
//                        Log.d("HomeFragment", "Fetched ${products.size} products for $productName")
//                    }
//                    productsFetched++
//
//                    // Check if all fetch attempts are done, then call setupViewPager
//                    if (productsFetched == totalProducts) {
//                        Log.d("HomeFragment", "All fetch attempts completed. Fetched ${fetchedProducts.size} total products.")
//                        onProductsFetched(fetchedProducts)
//                    }
//                },
//                { error ->
//                    Log.e("HomeFragment", "Error fetching product $productName: ${error.message}")
//                    productsFetched++
//
//                    // Check if all fetch attempts are done, even if some failed
//                    if (productsFetched == totalProducts) {
//                        Log.d("HomeFragment", "All fetch attempts completed with some errors. Fetched ${fetchedProducts.size} total products.")
//                        onProductsFetched(fetchedProducts)
//                    }
//                }
//            )
//        }
//    }
//
//
//    private fun setupViewPager(products: List<SkinCareProduct>) {
//        Log.d("HomeFragment", "Setting up ViewPager with ${products.size} products.")
//        if (products.isEmpty()) {
//            Log.e("HomeFragment", "No products available to display.")
//            return
//        }
//
//        recommendationPagerAdapter = RecommendationPagerAdapter(products)
//        viewPager.adapter = recommendationPagerAdapter
//    }
//}
//
//

