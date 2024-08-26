package khan.z.dermagazeai.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


//class MainActivity : AppCompatActivity() {
//
//    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.startupFragment,
//                R.id.signupFragment,
//                R.id.loginFragment,
//                R.id.confirmationFragment,
//                R.id.homeFragment
//                -> supportActionBar?.hide()
//                else -> {
//                    supportActionBar?.show()
//                    setupActionBarWithNavController(navController, appBarConfiguration)
//                }
//            }
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Amplify.Auth.handleWebUISignInResponse(data)
//    }
//}