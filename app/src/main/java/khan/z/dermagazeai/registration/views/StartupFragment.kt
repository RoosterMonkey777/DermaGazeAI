package khan.z.dermagazeai.registration.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.R

class StartupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin: View = view.findViewById(R.id.buttonLogin)
        val buttonSignup: View = view.findViewById(R.id.buttonSignup)

        buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startupFragment_to_loginFragment)
        }

        buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.action_startupFragment_to_signupFragment)
        }
    }
}
