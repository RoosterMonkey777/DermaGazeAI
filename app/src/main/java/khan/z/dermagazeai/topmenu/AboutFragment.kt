package khan.z.dermagazeai.topmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import khan.z.dermagazeai.R

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back Button
        val backButton: ImageButton = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Hide BottomNavigationView
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Restore BottomNavigationView
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE
    }
}
