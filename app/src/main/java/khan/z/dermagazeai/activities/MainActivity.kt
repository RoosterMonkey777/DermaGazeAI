package khan.z.dermagazeai.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amplifyframework.core.Amplify
import com.google.android.material.bottomnavigation.BottomNavigationView
import khan.z.dermagazeai.R


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


