package khan.z.dermagazeai.medication.views

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Medication
import khan.z.dermagazeai.R

class SearchMedicationFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioSearchByDIN: RadioButton
    private lateinit var radioSearchByName: RadioButton
    private lateinit var resultsContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_medication, container, false)

        // Initialize UI elements
        searchInput = view.findViewById(R.id.search_input)
        searchButton = view.findViewById(R.id.search_button)
        radioGroup = view.findViewById(R.id.search_radio_group)
        radioSearchByDIN = view.findViewById(R.id.radio_search_by_din)
        radioSearchByName = view.findViewById(R.id.radio_search_by_name)
        resultsContainer = view.findViewById(R.id.results_container)

        // Set the initial state to DIN search and make the numeric keyboard appear
        radioSearchByDIN.isChecked = true
        setSearchInputForDIN()

        // Add a listener for when the user changes between Name and DIN search
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            // Reset the text input and clear results container when switching between DIN and Name
            searchInput.text.clear()
            clearPreviousResults()

            // If searching by DIN, restrict input to 8 digits
            if (checkedId == R.id.radio_search_by_din) {
                setSearchInputForDIN()
            } else if (checkedId == R.id.radio_search_by_name) {
                setSearchInputForName()
            }
        }

        // Search button onClick listener
        searchButton.setOnClickListener {
            performSearch()
        }

        return view
    }

    // Perform the search based on the input and selected radio button
    private fun performSearch() {
        val query = searchInput.text.toString().trim()

        if (query.isEmpty()) {
            // Handle empty input
            Toast.makeText(context, "Please enter a DIN or Name", Toast.LENGTH_SHORT).show()
            return
        }

        // Check which RadioButton is selected and perform the search accordingly
        if (radioSearchByDIN.isChecked) {
            Log.d("MedicationSearch", "Searching by DIN: $query")
            searchMedicationByDIN(query)
        } else if (radioSearchByName.isChecked) {
            val uppercaseQuery = query.uppercase()  // Convert to uppercase for case-insensitive search
            Log.d("MedicationSearch", "Searching by Name: $uppercaseQuery")
            searchMedicationByName(uppercaseQuery)
        }
    }

    // Function to search by DIN using GraphQL
    private fun searchMedicationByDIN(din: String) {
        Log.d("MedicationSearch", "Executing query for DIN: '$din'")

        Amplify.API.query(
            ModelQuery.list(
                Medication::class.java,
                Medication.DRUG_IDENTIFICATION_NUMBER.eq(din)  // Exact match with DIN
            ),
            { response ->
                Log.d("MedicationSearch", "Query Response: $response")

                val medications = response.data?.items
                if (medications != null && medications.iterator().hasNext()) {
                    requireActivity().runOnUiThread {
                        displayAllResults(medications.toList())
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Medication not found", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                requireActivity().runOnUiThread {
                    Log.e("MedicationSearch", "Query failed: ${error.message}")
                    Toast.makeText(context, "Error retrieving medication", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Function to search by Brand Name using GraphQL
    private fun searchMedicationByName(name: String) {
        Log.d("MedicationSearch", "Executing query for Brand Name (contains): '$name'")

        Amplify.API.query(
            ModelQuery.list(
                Medication::class.java,
                Medication.BRAND_NAME.contains(name)  // Use contains for partial/fuzzy matching
            ),
            { response ->
                Log.d("MedicationSearch", "Query Response: $response")

                val medications = response.data?.items
                if (medications != null && medications.iterator().hasNext()) {
                    requireActivity().runOnUiThread {
                        displayAllResults(medications.toList())
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Medication not found", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                requireActivity().runOnUiThread {
                    Log.e("MedicationSearch", "Query failed: ${error.message}")
                    Toast.makeText(context, "Error retrieving medication", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Function to dynamically display all results
    private fun displayAllResults(medications: List<Medication>) {
        resultsContainer.removeAllViews()

        for (medication in medications) {
            val cardView = layoutInflater.inflate(R.layout.medication_result_card, null) as CardView

            val resultName = cardView.findViewById<TextView>(R.id.result_name)
            val resultCompany = cardView.findViewById<TextView>(R.id.result_company)
            val resultDIN = cardView.findViewById<TextView>(R.id.result_din)
            val expandButton = cardView.findViewById<ImageButton>(R.id.expand_button)

            resultName.text = medication.brandName
            resultCompany.text = medication.companyName
            resultDIN.text = "DIN: ${medication.drugIdentificationNumber}"

            // Add click listener to the expand button (+ button)
            expandButton.setOnClickListener {
                // Navigate to MedicationDetailsFragment, passing the medication info using Safe Args
                val action = SearchMedicationFragmentDirections
                    .actionSearchMedicationFragmentToMedicationDetailsFragment(
                        medicationName = medication.brandName,
                        medicationCompany = medication.companyName,
                        medicationDIN = medication.drugIdentificationNumber
                    )
                findNavController().navigate(action)
            }

            resultsContainer.addView(cardView)
        }
    }

    // Function to set search input for DIN
    private fun setSearchInputForDIN() {
        // Restrict DIN input to 8 digits and show the numeric keyboard
        searchInput.filters = arrayOf(InputFilter.LengthFilter(8))
        searchInput.inputType = InputType.TYPE_CLASS_NUMBER  // Set numeric input
        searchInput.hint = "Enter 8-digit DIN"
    }

    // Function to set search input for Name
    private fun setSearchInputForName() {
        // Remove input restrictions for name and show the regular keyboard
        searchInput.filters = arrayOf()
        searchInput.inputType = InputType.TYPE_CLASS_TEXT  // Set text input
        searchInput.hint = "Enter Medication Name"
    }

    // Function to clear the previous search results
    private fun clearPreviousResults() {
        resultsContainer.removeAllViews()
    }
}



//class SearchMedicationFragment : Fragment() {
//
//    private lateinit var searchInput: EditText
//    private lateinit var searchButton: Button
//    private lateinit var radioGroup: RadioGroup
//    private lateinit var radioSearchByDIN: RadioButton
//    private lateinit var radioSearchByName: RadioButton
//    private lateinit var resultsContainer: LinearLayout
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_search_medication, container, false)
//
//        // Initialize UI elements
//        searchInput = view.findViewById(R.id.search_input)
//        searchButton = view.findViewById(R.id.search_button)
//        radioGroup = view.findViewById(R.id.search_radio_group)
//        radioSearchByDIN = view.findViewById(R.id.radio_search_by_din)
//        radioSearchByName = view.findViewById(R.id.radio_search_by_name)
//        resultsContainer = view.findViewById(R.id.results_container)
//
//        // Set the initial state to DIN search and make the numeric keyboard appear
//        radioSearchByDIN.isChecked = true
//        setSearchInputForDIN()
//
//        // Add a listener for when the user changes between Name and DIN search
//        radioGroup.setOnCheckedChangeListener { _, checkedId ->
//            // Reset the text input and clear results container when switching between DIN and Name
//            searchInput.text.clear()
//            clearPreviousResults()
//
//            // If searching by DIN, restrict input to 8 digits
//            if (checkedId == R.id.radio_search_by_din) {
//                setSearchInputForDIN()
//            } else if (checkedId == R.id.radio_search_by_name) {
//                setSearchInputForName()
//            }
//        }
//
//        // Search button onClick listener
//        searchButton.setOnClickListener {
//            performSearch()
//        }
//
//        return view
//    }
//
//    // Perform the search based on the input and selected radio button
//    private fun performSearch() {
//        val query = searchInput.text.toString().trim()
//
//        if (query.isEmpty()) {
//            // Handle empty input
//            Toast.makeText(context, "Please enter a DIN or Name", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Check which RadioButton is selected and perform the search accordingly
//        if (radioSearchByDIN.isChecked) {
//            Log.d("MedicationSearch", "Searching by DIN: $query")
//            searchMedicationByDIN(query)
//        } else if (radioSearchByName.isChecked) {
//            val uppercaseQuery = query.uppercase()  // Convert to uppercase for case-insensitive search
//            Log.d("MedicationSearch", "Searching by Name: $uppercaseQuery")
//            searchMedicationByName(uppercaseQuery)
//        }
//    }
//
//    // Function to search by DIN using GraphQL
//    private fun searchMedicationByDIN(din: String) {
//        Log.d("MedicationSearch", "Executing query for DIN: '$din'")
//
//        Amplify.API.query(
//            ModelQuery.list(
//                Medication::class.java,
//                Medication.DRUG_IDENTIFICATION_NUMBER.eq(din)  // Exact match with DIN
//            ),
//            { response ->
//                Log.d("MedicationSearch", "Query Response: $response")
//
//                val medications = response.data?.items
//                if (medications != null && medications.iterator().hasNext()) {
//                    requireActivity().runOnUiThread {
//                        displayAllResults(medications.toList())
//                    }
//                } else {
//                    requireActivity().runOnUiThread {
//                        Toast.makeText(context, "Medication not found", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            },
//            { error ->
//                requireActivity().runOnUiThread {
//                    Log.e("MedicationSearch", "Query failed: ${error.message}")
//                    Toast.makeText(context, "Error retrieving medication", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//    }
//
//    // Function to search by Brand Name using GraphQL
//    private fun searchMedicationByName(name: String) {
//        Log.d("MedicationSearch", "Executing query for Brand Name (contains): '$name'")
//
//        Amplify.API.query(
//            ModelQuery.list(
//                Medication::class.java,
//                Medication.BRAND_NAME.contains(name)  // Use contains for partial/fuzzy matching
//            ),
//            { response ->
//                Log.d("MedicationSearch", "Query Response: $response")
//
//                val medications = response.data?.items
//                if (medications != null && medications.iterator().hasNext()) {
//                    requireActivity().runOnUiThread {
//                        displayAllResults(medications.toList())
//                    }
//                } else {
//                    requireActivity().runOnUiThread {
//                        Toast.makeText(context, "Medication not found", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            },
//            { error ->
//                requireActivity().runOnUiThread {
//                    Log.e("MedicationSearch", "Query failed: ${error.message}")
//                    Toast.makeText(context, "Error retrieving medication", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//    }
//
//    // Function to dynamically display all results
////    private fun displayAllResults(medications: List<Medication>) {
////        // Clear any previous results
////        resultsContainer.removeAllViews()
////
////        // For each medication, inflate a card and add it to the results container
////        for (medication in medications) {
////            // Inflate the card layout for each medication
////            val cardView = layoutInflater.inflate(R.layout.medication_result_card, null) as CardView
////
////            // Set the medication details in the card
////            val resultName = cardView.findViewById<TextView>(R.id.result_name)
////            val resultCompany = cardView.findViewById<TextView>(R.id.result_company)
////            val resultDIN = cardView.findViewById<TextView>(R.id.result_din)
////
////            resultName.text = medication.brandName
////            resultCompany.text = medication.companyName
////            resultDIN.text = "DIN: ${medication.drugIdentificationNumber}"
////
////            // Add the card to the container
////            resultsContainer.addView(cardView)
////        }
////
////        // Notify the user of how many medications were found
////        Toast.makeText(context, "Found ${medications.size} medications", Toast.LENGTH_SHORT).show()
////    }
//    // Inside the displayAllResults() method of SearchMedicationFragment
//    private fun displayAllResults(medications: List<Medication>) {
//        resultsContainer.removeAllViews()
//
//        for (medication in medications) {
//            val cardView = layoutInflater.inflate(R.layout.medication_result_card, null) as CardView
//
//            val resultName = cardView.findViewById<TextView>(R.id.result_name)
//            val resultCompany = cardView.findViewById<TextView>(R.id.result_company)
//            val resultDIN = cardView.findViewById<TextView>(R.id.result_din)
//            val expandButton = cardView.findViewById<ImageButton>(R.id.expand_button)
//
//            resultName.text = medication.brandName
//            resultCompany.text = medication.companyName
//            resultDIN.text = "DIN: ${medication.drugIdentificationNumber}"
//
//            // Add click listener to the expand button (+ button)
//            expandButton.setOnClickListener {
//                // Navigate to MedicationDetailsFragment, passing the medication info
//                val action = SearchMedicationFragmentDirections
//                    .actionSearchMedicationFragmentToMedicationDetailsFragment(
//                        medicationName = medication.brandName,
//                        medicationCompany = medication.companyName,
//                        medicationDIN = medication.drugIdentificationNumber
//                    )
//                findNavController().navigate(action)
//            }
//
//            resultsContainer.addView(cardView)
//        }
//    }
//
//    // Function to set search input for DIN
//    private fun setSearchInputForDIN() {
//        // Restrict DIN input to 8 digits and show the numeric keyboard
//        searchInput.filters = arrayOf(InputFilter.LengthFilter(8))
//        searchInput.inputType = InputType.TYPE_CLASS_NUMBER  // Set numeric input
//        searchInput.hint = "Enter 8-digit DIN"
//    }
//
//    // Function to set search input for Name
//    private fun setSearchInputForName() {
//        // Remove input restrictions for name and show the regular keyboard
//        searchInput.filters = arrayOf()
//        searchInput.inputType = InputType.TYPE_CLASS_TEXT  // Set text input
//        searchInput.hint = "Enter Medication Name"
//    }
//
//    // Function to clear the previous search results
//    private fun clearPreviousResults() {
//        resultsContainer.removeAllViews()
//    }
//}