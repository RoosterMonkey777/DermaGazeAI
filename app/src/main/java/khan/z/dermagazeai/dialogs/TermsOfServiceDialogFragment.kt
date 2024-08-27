package khan.z.dermagazeai.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import khan.z.dermagazeai.R
import khan.z.dermagazeai.registration.helpers.AuthorizationUtils

class TermsOfServiceDialogFragment : DialogFragment() {

    private lateinit var checkboxAgree: CheckBox
    private lateinit var btnAccept: Button
    private lateinit var btnDecline: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_terms_of_service_dialog, container, false)

        checkboxAgree = view.findViewById(R.id.checkbox_agree)
        btnAccept = view.findViewById(R.id.btn_accept)
        btnDecline = view.findViewById(R.id.btn_decline)

        // Enable Accept button only when checkbox is checked
        checkboxAgree.setOnCheckedChangeListener { _, isChecked ->
            btnAccept.isEnabled = isChecked
        }

        btnAccept.setOnClickListener {
            // Proceed to UserProfileDialogFragment, but don't save consentGiven yet
            findNavController().navigate(R.id.action_termsOfServiceDialogFragment_to_userProfileDialogFragment)
           // dismiss()
        }

        btnDecline.setOnClickListener {
            // Use the AuthorizationUtils to handle sign out
            AuthorizationUtils.signOut(requireContext()) {
                // Navigate back to login screen after sign out
                requireActivity().runOnUiThread {
                    findNavController().navigate(R.id.action_termsOfServiceDialogFragment_to_loginFragment)
                }
            }
        }

        return view
    }
}