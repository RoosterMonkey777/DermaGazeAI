package khan.z.dermagazeai.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import com.google.android.material.bottomnavigation.BottomNavigationView
import khan.z.dermagazeai.R
import khan.z.dermagazeai.onboard.OnboardingScreen
import khan.z.dermagazeai.onboard.OnboardingUtils
import khan.z.dermagazeai.ui.theme.OnboardingJetpackComposeTheme
import kotlinx.coroutines.launch


/*class MainActivity : AppCompatActivity() {

    private val onboardingUtils by lazy { OnboardingUtils(this) }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView

    private fun checkLoginState() {
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // User is already logged in, navigate to the home page directly
            setupNavigation()  // Call the function that sets up the home navigation
            bottomNavigationView.visibility = View.VISIBLE  // Show bottom navigation if needed
        } else {
            // Show onboarding or login
            if (onboardingUtils.isOnboardingCompleted()) {
                setupNavigation()
            } else {
                showOnboardingCompose()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        // Check login state
        checkLoginState()
    }




    private fun showOnboardingCompose() {
        val composeView = findViewById<androidx.compose.ui.platform.ComposeView>(R.id.compose_view)
        composeView.setContent {
            OnboardingJetpackComposeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ShowOnboardingScreen()
                }
            }
        }
    }

    private fun hideOnboardingCompose() {
        val composeView = findViewById<androidx.compose.ui.platform.ComposeView>(R.id.compose_view)
        composeView.visibility = View.GONE // Hide the Compose view after onboarding
    }

    fun showBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE // Show the bottom navigation after login
    }

    @Composable
    private fun ShowOnboardingScreen() {
        val scope = rememberCoroutineScope()
        OnboardingScreen {
            onboardingUtils.setOnboardingCompleted()
            scope.launch {
                hideOnboardingCompose()  // Hide the onboarding screen
                setupNavigation()  // Setup navigation
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))

        // Control when the BottomNavigationView is visible based on the destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                bottomNavigationView.visibility = View.VISIBLE
            } else {
                bottomNavigationView.visibility = View.GONE
            }
        }

        // Bottom Navigation item click handling
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                    true
                }
                R.id.nav_camera -> {
                    if (navController.currentDestination?.id != R.id.cameraFragment) {
                        navController.navigate(R.id.cameraFragment)
                    }
                    true
                }
                R.id.nav_medication -> {
                    if (navController.currentDestination?.id != R.id.medicationFragment) {
                        navController.navigate(R.id.medicationFragment)
                    }
                    true
                }
                R.id.nav_profile -> {
                    if (navController.currentDestination?.id != R.id.userProfileFragment) {
                        navController.navigate(R.id.userProfileFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}*/

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up AppBarConfiguration with top-level destinations
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment, //R.id.cameraFragment, R.id.medicationFragment, R.id.profileFragment
        ))

        // Handle the visibility of the bottom navigation based on the destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.startupFragment,
                R.id.signupFragment,
                R.id.loginFragment,
                R.id.confirmationFragment -> {
                    findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
                }
                else -> {
                    findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
                }
            }
        }

        // Setup bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.homeFragment)
                    }
                    true
                }
                R.id.nav_camera -> {
                    if (navController.currentDestination?.id != R.id.cameraFragment) {
                        navController.navigate(R.id.cameraFragment)
                    }
                    true
                }
                R.id.nav_medication -> {
                    if (navController.currentDestination?.id != R.id.medicationFragment) {
                        navController.navigate(R.id.medicationFragment)
                    }
                    true
                }
                R.id.nav_profile -> {
                    if (navController.currentDestination?.id != R.id.userProfileFragment) {
                        navController.navigate(R.id.userProfileFragment)
                    }
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


