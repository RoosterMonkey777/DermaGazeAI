package khan.z.dermagazeai.registration.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import khan.z.dermagazeai.R

class ConfirmationFragment : Fragment() {

    private lateinit var etConfirmationCode: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etConfirmationCode = view.findViewById(R.id.confirmation_code_edit_text)

        val email = arguments?.getString("email") ?: return

        view.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            confirmSignUp(email)
        }
    }

    private fun confirmSignUp(email: String) {
        val confirmationCode = etConfirmationCode.text.toString()

        Amplify.Auth.confirmSignUp(email, confirmationCode,
            { result ->
                activity?.runOnUiThread {
                    if (result.isSignUpComplete) {
                        Toast.makeText(context, "Confirmation verified", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_confirmationFragment_to_loginFragment)
                    } else {
                        Toast.makeText(context, "Confirmation invalid, redirecting back to signup", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_confirmationFragment_to_signupFragment)
                    }
                }
            },
            { error ->
                activity?.runOnUiThread {
                    Toast.makeText(context, "Confirmation failed: ${error.toString()}", Toast.LENGTH_SHORT).show()
                    Log.e("ConfirmSignUpFailed", error.toString())
                }
            }
        )
    }
}