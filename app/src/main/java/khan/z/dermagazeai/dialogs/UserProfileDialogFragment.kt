package khan.z.dermagazeai.dialogs

// Handles the UI and logic for capturing the user's profile information

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import khan.z.dermagazeai.R

class UserProfileDialogFragment : DialogFragment() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile_dialog, container, false)

        etFirstName = view.findViewById(R.id.et_firstname)
        etLastName = view.findViewById(R.id.et_lastname)
        etEmail = view.findViewById(R.id.et_email)
        etAge = view.findViewById(R.id.et_age)
        etGender = view.findViewById(R.id.et_gender)
        saveButton = view.findViewById(R.id.btn_save)
        cancelButton = view.findViewById(R.id.btn_cancel)

        // Fetch the current user's email
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.firstOrNull { it.key.keyString == "email" }?.value
                requireActivity().runOnUiThread {
                    etEmail.setText(email)
                }
            },
            { error ->
                Log.e("UserProfileDialog", "Failed to fetch user attributes", error)
            }
        )

        saveButton.setOnClickListener {
            saveUserData()
        }

        cancelButton.setOnClickListener {
            Amplify.Auth.signOut {
                requireActivity().runOnUiThread {
                    findNavController().navigate(R.id.action_userProfileDialogFragment_to_loginFragment)
                }
            }
        }

        return view
    }

    private fun saveUserData() {
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()
        val age = etAge.text.toString().toIntOrNull()
        val gender = etGender.text.toString()

        val user = User.builder()
            .firstname(firstName)
            .lastname(lastName)
            .email(email)
            .age(age)
            .gender(gender)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            { response ->
                if (response.hasErrors()) {
                    Log.e("UserProfileDialog", "Failed to create user: ${response.errors.first().message}")
                } else {
                    requireActivity().runOnUiThread {
                        findNavController().navigate(R.id.action_userProfileDialogFragment_to_homeFragment)
                    }
                }
            },
            { error ->
                Log.e("UserProfileDialog", "Failed to create user", error)
            }
        )

    }
}