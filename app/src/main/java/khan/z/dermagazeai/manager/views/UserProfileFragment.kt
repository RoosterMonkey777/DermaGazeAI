package khan.z.dermagazeai.manager.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amplifyframework.datastore.generated.model.UserProfile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import khan.z.dermagazeai.R
import khan.z.dermagazeai.activities.MainActivity
import khan.z.dermagazeai.manager.UserProfileManager

class UserProfileFragment : Fragment() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var tvEmail: TextView
    private lateinit var spinnerSkinType: Spinner
    private lateinit var spinnerProductType: Spinner
    private lateinit var chipGroupSkinProblems: ChipGroup
    private lateinit var chipGroupNotableEffects: ChipGroup
    private lateinit var updateButton: Button

    private val userProfileManager = UserProfileManager()
    private var currentUser: UserProfile? = null  // To store the current user profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components
        tvEmail = view.findViewById(R.id.tv_email)
        etFirstName = view.findViewById(R.id.et_firstname)
        etLastName = view.findViewById(R.id.et_lastname)
        etAge = view.findViewById(R.id.et_age)
        etGender = view.findViewById(R.id.et_gender)
        spinnerSkinType = view.findViewById(R.id.spinner_skin_type)
        spinnerProductType = view.findViewById(R.id.spinner_product_type)
        chipGroupSkinProblems = view.findViewById(R.id.chip_group_skin_problems)
        chipGroupNotableEffects = view.findViewById(R.id.chip_group_notable_effects)
        updateButton = view.findViewById(R.id.btn_update)

        // Fetch and display current user data
        fetchAndPopulateUserData()

        // Set up the update button click listener
        updateButton.setOnClickListener {
            updateUserData()
        }



    }


    private fun updateUserData() {
        Log.d("UserProfileFragment", "updateUserData called")

        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = tvEmail.text.toString()
        val age = etAge.text.toString().toIntOrNull()
        val gender = etGender.text.toString()
        val skinType = spinnerSkinType.selectedItem.toString()
        val productCategory = spinnerProductType.selectedItem.toString()
        val selectedProblems = chipGroupSkinProblems.checkedChipIds.map { id ->
            chipGroupSkinProblems.findViewById<Chip>(id).text.toString()
        }
        val selectedNotableEffects = chipGroupNotableEffects.checkedChipIds.map { id ->
            chipGroupNotableEffects.findViewById<Chip>(id).text.toString()
        }

        userProfileManager.updateUser(
            firstName,
            lastName,
            email,
            age,
            gender,
            skinType,
            productCategory,
            selectedProblems,
            selectedNotableEffects,
            true,
            onSuccess = {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Successfully updated user", Toast.LENGTH_SHORT).show()
                    Log.e("UserProfileDialog", "Successfully updated user")
                    findNavController().navigate(R.id.action_userProfileFragment_to_homeFragment)

                    // Update the BottomNavigationView to highlight the Home icon
                    (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.selectedItemId = R.id.nav_home
                }
            },
            onError = { error: Exception ->
                Log.e("UserProfileDialog", "Failed to update user", error)
            }
        )
    }


    private fun fetchAndPopulateUserData() {
        userProfileManager.fetchUserEmail(
            onSuccess = { email ->
                tvEmail.text = email
                userProfileManager.checkUserProfile(
                    email,
                    onProfileFound = { user ->
                        currentUser = user
                        requireActivity().runOnUiThread {
                            etFirstName.setText(user.firstname)
                            etLastName.setText(user.lastname)
                            etAge.setText(user.age?.toString())
                            etGender.setText(user.gender)
                            populateSpinnersAndChips(user)  // Move this inside runOnUiThread
                        }
                    },
                    onProfileNotFound = {
                        Log.e("UserProfileFragment", "User profile not found")
                    },
                    onError = { error ->
                        Log.e("UserProfileFragment", "Failed to fetch user profile", error)
                    }
                )
            },
            onError = { error ->
                Log.e("UserProfileFragment", "Failed to fetch user email", error)
            }
        )
    }

    private fun populateSpinnersAndChips(user: UserProfile) {
        requireActivity().runOnUiThread {
            userProfileManager.populateSkinTypeSpinner(requireContext(), spinnerSkinType)
            spinnerSkinType.setSelection(getIndexForSpinner(spinnerSkinType, user.skintype))

            userProfileManager.populateProductCategorySpinner(requireContext(), spinnerProductType)
            spinnerProductType.setSelection(getIndexForSpinner(spinnerProductType, user.productType))

            userProfileManager.populateSkinProblemsChipGroup(requireContext(), chipGroupSkinProblems, user.skinProblems)
        }


        // Set listeners to update notable effects based on skin type and product type
        spinnerSkinType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                userProfileManager.updateNotableEffects(
                    spinnerSkinType.selectedItem?.toString(),
                    spinnerProductType.selectedItem?.toString(),
                    chipGroupNotableEffects,
                    requireContext()
                )
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerProductType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                userProfileManager.updateNotableEffects(
                    spinnerSkinType.selectedItem?.toString(),
                    spinnerProductType.selectedItem?.toString(),
                    chipGroupNotableEffects,
                    requireContext()
                )
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun getIndexForSpinner(spinner: Spinner, value: String?): Int {
        return (spinner.adapter as ArrayAdapter<String>).getPosition(value)
    }
}

