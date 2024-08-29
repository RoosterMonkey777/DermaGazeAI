package khan.z.dermagazeai.dialogs

// Handles the UI and logic for capturing the user's profile information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.SkinCareProduct
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import khan.z.dermagazeai.R
import khan.z.dermagazeai.manager.UserProfileManager


class UserProfileDialogFragment : DialogFragment() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etGender: EditText
    private lateinit var spinnerSkinType: Spinner
    private lateinit var spinnerProductType: Spinner
    private lateinit var chipGroupSkinProblems: ChipGroup
    private lateinit var chipGroupNotableEffects: ChipGroup
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private val userProfileManager = UserProfileManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_profile_dialog, container, false)

        // Initialize UI components
        etFirstName = view.findViewById(R.id.et_firstname)
        etLastName = view.findViewById(R.id.et_lastname)
        etEmail = view.findViewById(R.id.et_email)
        etAge = view.findViewById(R.id.et_age)
        etGender = view.findViewById(R.id.et_gender)
        spinnerSkinType = view.findViewById(R.id.spinner_skin_type)
        spinnerProductType = view.findViewById(R.id.spinner_product_type)
        chipGroupSkinProblems = view.findViewById(R.id.chip_group_skin_problems)
        chipGroupNotableEffects = view.findViewById(R.id.chip_group_notable_effects)
        saveButton = view.findViewById(R.id.btn_save)
        cancelButton = view.findViewById(R.id.btn_cancel)

        // Populate UI components using UserProfileManager
        userProfileManager.populateSkinTypeSpinner(requireContext(), spinnerSkinType)
        userProfileManager.populateProductCategorySpinner(requireContext(), spinnerProductType)
        userProfileManager.populateSkinProblemsChipGroup(requireContext(), chipGroupSkinProblems, null)

        // Set listeners for spinners to update notable effects based on selection
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileManager.fetchUserEmail(
            onSuccess = { email ->
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        etEmail.setText(email)
                    }
                }
            },
            onError = { error ->
                Log.e("UserProfileDialog", "Failed to fetch user email", error)
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
    }

    private fun saveUserData() {
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()
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

        userProfileManager.createUser(
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
                    findNavController().navigate(R.id.action_userProfileDialogFragment_to_homeFragment)
                }
            },
            onError = { error: Exception ->
                Log.e("UserProfileDialog", "Failed to create user", error)
            }
        )
    }
}



//class UserProfileDialogFragment : DialogFragment() {
//
//    private lateinit var etFirstName: EditText
//    private lateinit var etLastName: EditText
//    private lateinit var etEmail: EditText
//    private lateinit var etAge: EditText
//    private lateinit var etGender: EditText
//    private lateinit var spinnerSkinType: Spinner
//    private lateinit var spinnerProductType: Spinner
//    private lateinit var chipGroupSkinProblems: ChipGroup
//    private lateinit var chipGroupNotableEffects: ChipGroup
//    private lateinit var saveButton: Button
//    private lateinit var cancelButton: Button
//    private val userProfileManager = UserProfileManager()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_user_profile_dialog, container, false)
//
//        // Initialize UI components
//        etFirstName = view.findViewById(R.id.et_firstname)
//        etLastName = view.findViewById(R.id.et_lastname)
//        etEmail = view.findViewById(R.id.et_email)
//        etAge = view.findViewById(R.id.et_age)
//        etGender = view.findViewById(R.id.et_gender)
//        spinnerSkinType = view.findViewById(R.id.spinner_skin_type)
//        spinnerProductType = view.findViewById(R.id.spinner_product_type)
//        chipGroupSkinProblems = view.findViewById(R.id.chip_group_skin_problems)
//        chipGroupNotableEffects = view.findViewById(R.id.chip_group_notable_effects)
//        saveButton = view.findViewById(R.id.btn_save)
//        cancelButton = view.findViewById(R.id.btn_cancel)
//
//        // Populate Spinners and ChipGroup
//        populateSkinTypeSpinner()
//        populateProductCategorySpinner()
//        populateSkinProblemsChipGroup()
//
//        // Set listeners for spinners to update notable effects based on selection
//        spinnerSkinType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                updateNotableEffects()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }
//
//        spinnerProductType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                updateNotableEffects()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Fetch the current user's email using UserProfileManager and update the UI
//        userProfileManager.fetchUserEmail(
//            onSuccess = { email ->
//                if (isAdded) {
//                    requireActivity().runOnUiThread {
//                        etEmail.setText(email)
//                    }
//                }
//            },
//            onError = { error ->
//                Log.e("UserProfileDialog", "Failed to fetch user email", error)
//            }
//        )
//
//        // Set up the save button click listener
//        saveButton.setOnClickListener {
//            saveUserData()
//        }
//
//        // Set up the cancel button click listener
//        cancelButton.setOnClickListener {
//            Amplify.Auth.signOut {
//                requireActivity().runOnUiThread {
//                    findNavController().navigate(R.id.action_userProfileDialogFragment_to_loginFragment)
//                }
//            }
//        }
//    }
//
//    private fun populateSkinTypeSpinner() {
//        val skinTypes = listOf("Normal", "Dry", "Oily", "Combination", "Sensitive")
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, skinTypes)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerSkinType.adapter = adapter
//    }
//
//    private fun populateProductCategorySpinner() {
//        val productCategories = listOf("Face Wash", "Toner", "Serum", "Moisturizer", "Sunscreen")
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productCategories)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerProductType.adapter = adapter
//    }
//
//    private fun populateSkinProblemsChipGroup() {
//        val skinProblems = listOf(
//            "Dull Skin", "Acne", "Acne Scars", "Large Pores", "Blackheads",
//            "Uneven Skin Tone", "Redness", "Fine Lines and Wrinkles", "Sagging Skin", "Dark Spots"
//        )
//        skinProblems.forEach { problem ->
//            val chip = Chip(requireContext())
//            chip.text = problem
//            chip.isCheckable = true
//            chipGroupSkinProblems.addView(chip)
//        }
//    }
//    private fun updateNotableEffects() {
//        val selectedSkinType = spinnerSkinType.selectedItem?.toString()
//        val selectedProductType = spinnerProductType.selectedItem?.toString()
//
//        // Log the selected values
//        Log.d("UserProfileDialog", "Selected skin type: $selectedSkinType")
//        Log.d("UserProfileDialog", "Selected product type: $selectedProductType")
//
//        if (selectedSkinType == null || selectedProductType == null) {
//            Log.e("UserProfileDialog", "Skin type or Product type not selected.")
//            return
//        }
//
//        // Query to filter products based on selected skin type and product type
//        Amplify.API.query(
//            ModelQuery.list(
//                SkinCareProduct::class.java,
//                SkinCareProduct.SKINTYPE.contains(selectedSkinType)
//                    .and(SkinCareProduct.PRODUCT_TYPE.eq(selectedProductType))
//            ),
//            { response ->
//                val items = response.data?.items
//
//                // Log the number of items found
//                if (items != null && items.iterator().hasNext()) {
//
//                    // Log each product found for more insight
//                    items.forEach { product ->
//                        Log.d("UserProfileDialog", "Product found: ${product.productName}, notable effects: ${product.notableEffects}")
//                    }
//
//                    // Extract and log unique notable effects
//                    val notableEffects = items.flatMap { it.notableEffects ?: emptyList() }.distinct()
//                    Log.d("UserProfileDialog", "Unique notable effects: $notableEffects")
//
//                    // Update UI with notable effects
//                    requireActivity().runOnUiThread {
//                        chipGroupNotableEffects.removeAllViews()
//                        notableEffects.forEach { effect ->
//                            val chip = Chip(requireContext())
//                            chip.text = effect
//                            chip.isCheckable = true
//                            chipGroupNotableEffects.addView(chip)
//                        }
//                    }
//                } else {
//                    // No products found
//                    Log.w("UserProfileDialog", "No matching products found.")
//                }
//            },
//            { error ->
//                Log.e("UserProfileDialog", "Failed to fetch notable effects", error)
//            }
//        )
//    }
//
//    private fun saveUserData() {
//        val firstName = etFirstName.text.toString()
//        val lastName = etLastName.text.toString()
//        val email = etEmail.text.toString()
//        val age = etAge.text.toString().toIntOrNull()
//        val gender = etGender.text.toString()
//        val skinType = spinnerSkinType.selectedItem.toString()
//        val productCategory = spinnerProductType.selectedItem.toString()
//
//        // Get selected skin problems
//        val selectedProblems = mutableListOf<String>()
//        chipGroupSkinProblems.checkedChipIds.forEach { chipId ->
//            val chip = chipGroupSkinProblems.findViewById<Chip>(chipId)
//            selectedProblems.add(chip.text.toString())
//        }
//
//        // Get selected notable effects
//        val selectedNotableEffects = mutableListOf<String>()
//        chipGroupNotableEffects.checkedChipIds.forEach { chipId ->
//            val chip = chipGroupNotableEffects.findViewById<Chip>(chipId)
//            selectedNotableEffects.add(chip.text.toString())
//        }
//
//        userProfileManager.createUser(
//            firstName,
//            lastName,
//            email,
//            age,
//            gender,
//            skinType,
//            productCategory,
//            selectedProblems,
//            selectedNotableEffects,
//            true, // consentGiven is set to true
//            onSuccess = {
//                requireActivity().runOnUiThread {
//                    findNavController().navigate(R.id.action_userProfileDialogFragment_to_homeFragment)
//                }
//            },
//            onError = { error: Exception ->
//                Log.e("UserProfileDialog", "Failed to create user", error)
//            }
//        )
//    }
//}
