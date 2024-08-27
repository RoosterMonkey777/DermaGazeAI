package khan.z.dermagazeai.manager

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.helpers.AuthorizationUtils

class TopMenuManager(
    private val context: Context,
    private val fragment: Fragment, // Pass the fragment to handle navigation
    private val rotateButtonCallback: (Float, Float) -> Unit // Callback to handle button rotation
) {

    private var isRotated = false

    fun setupMenuButton(menuButton: ImageButton) {
        menuButton.setOnClickListener {
            if (!isRotated) {
                rotateButton(menuButton, 0f, 90f)
                showPopupMenu(menuButton)
            } else {
                rotateButton(menuButton, 90f, 0f)
            }
        }
    }

    private fun rotateButton(button: ImageButton, from: Float, to: Float) {
        rotateButtonCallback(from, to)
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(context, view)
        popup.menuInflater.inflate(R.menu.top_menu_button, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_faq -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_faqFragment)
                    true
                }
                R.id.action_about -> {
                    fragment.findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
                    true
                }
                R.id.action_signout -> {
                    AuthorizationUtils.signOut(context) {
                        fragment.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                    }
                    true
                }
                else -> false
            }
        }

        popup.setOnDismissListener {
            if (isRotated) {
                rotateButton(view as ImageButton, 90f, 0f)
                isRotated = false
            }
        }

        popup.show()

        isRotated = true
    }
}