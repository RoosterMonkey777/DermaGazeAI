package khan.z.dermagazeai.medication.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Medication
import com.amplifyframework.datastore.generated.model.UserMedication
import com.amplifyframework.datastore.generated.model.UserProfile
import khan.z.dermagazeai.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class MedicationDetailsFragment : Fragment() {

    private lateinit var medicationName: TextView
    private lateinit var medicationCompany: TextView
    private lateinit var medicationDIN: TextView
    private lateinit var dosageInput: EditText
    private lateinit var formInput: EditText
    private lateinit var startDateButton: Button
    private lateinit var endDateButton: Button
    private lateinit var timeButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var mondayCheckbox: CheckBox
    private lateinit var tuesdayCheckbox: CheckBox
    private lateinit var wednesdayCheckbox: CheckBox
    private lateinit var thursdayCheckbox: CheckBox
    private lateinit var fridayCheckbox: CheckBox
    private lateinit var saturdayCheckbox: CheckBox
    private lateinit var sundayCheckbox: CheckBox
    private var selectedStartDate: String = ""
    private var selectedEndDate: String = ""
    private var selectedTime: String = ""

    // Declare args variable to store the Safe Args
    private lateinit var args: MedicationDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medication_details, container, false)

        // Retrieve the Safe Args
        args = MedicationDetailsFragmentArgs.fromBundle(requireArguments())

        // Initialize UI elements
        medicationName = view.findViewById(R.id.medication_name)
        medicationCompany = view.findViewById(R.id.medication_company)
        medicationDIN = view.findViewById(R.id.medication_din)
        dosageInput = view.findViewById(R.id.dosage_input)
        formInput = view.findViewById(R.id.form_input)
        startDateButton = view.findViewById(R.id.select_start_date)
        endDateButton = view.findViewById(R.id.select_end_date)
        timeButton = view.findViewById(R.id.select_time_button)
        saveButton = view.findViewById(R.id.save_medication_button)
        cancelButton = view.findViewById(R.id.cancel_button)
        mondayCheckbox = view.findViewById(R.id.checkbox_monday)
        tuesdayCheckbox = view.findViewById(R.id.checkbox_tuesday)
        wednesdayCheckbox = view.findViewById(R.id.checkbox_wednesday)
        thursdayCheckbox = view.findViewById(R.id.checkbox_thursday)
        fridayCheckbox = view.findViewById(R.id.checkbox_friday)
        saturdayCheckbox = view.findViewById(R.id.checkbox_saturday)
        sundayCheckbox = view.findViewById(R.id.checkbox_sunday)

        // Set medication details from Safe Args
        medicationName.text = args.medicationName
        medicationCompany.text = args.medicationCompany
        medicationDIN.text = "DIN: ${args.medicationDIN}"

        // Handle cancel button click
        cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Set up start date picker
        startDateButton.setOnClickListener {
            showDatePicker { date ->
                selectedStartDate = date
                startDateButton.text = "Start Date: $date"
            }
        }

        // Set up end date picker
        endDateButton.setOnClickListener {
            showDatePicker { date ->
                selectedEndDate = date
                endDateButton.text = "End Date: $date"
            }
        }

        // Set up time picker
        timeButton.setOnClickListener {
            showTimePicker { time ->
                selectedTime = time
                timeButton.text = "Time: $time"
            }
        }

        // Save button click handler
        saveButton.setOnClickListener {
            saveMedicationDetails()
        }

        return view
    }

    // Function to show date picker
    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // Function to show time picker
    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                onTimeSelected(selectedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    // Function to save the medication details
    private fun saveMedicationDetails() {
        val dosage = dosageInput.text.toString()
        val form = formInput.text.toString()
        val daysOfWeek = getSelectedDaysOfWeek()

        if (dosage.isEmpty() || form.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Fetch the current user's profile ID
        getCurrentUserProfileId { userProfileId ->
            if (userProfileId != null) {
                // Save the medication for the user
                val userMedication = UserMedication.builder()
                    .userProfileId(userProfileId)
                    .medicationId(args.medicationDIN) // Medication DIN
                    .medicationName(args.medicationName) // Store medication name directly
                    .companyName(args.medicationCompany) // Store company name directly
                    .dosage(dosage)
                    .form(form)
                    .startDate(selectedStartDate)
                    .endDate(selectedEndDate)
                    .daysOfWeek(daysOfWeek)
                    .time(selectedTime)
                    .build()

                Amplify.API.mutate(
                    ModelMutation.create(userMedication),
                    { response ->
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Medication saved successfully", Toast.LENGTH_LONG).show()
                            findNavController().navigateUp()
                        }
                    },
                    { error ->
                        requireActivity().runOnUiThread {
                            Log.e("MedicationDetails", "Failed to save medication: ${error.message}")
                            Toast.makeText(requireContext(), "Failed to save medication", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            } else {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Failed to fetch user profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Function to retrieve the current user's profile ID
    private fun getCurrentUserProfileId(onSuccess: (String?) -> Unit) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.find { it.key.keyString == "email" }?.value
                if (email != null) {
                    // Use ModelQuery.list with a filter to search by email
                    val emailPredicate = UserProfile.EMAIL.eq(email)
                    Amplify.API.query(
                        ModelQuery.list(UserProfile::class.java, emailPredicate),
                        { response ->
                            val userProfile = response.data?.items?.firstOrNull()
                            val userId = userProfile?.id
                            onSuccess(userId)
                        },
                        { error ->
                            Log.e("MedicationDetails", "Failed to fetch user profile: ${error.message}")
                            onSuccess(null)
                        }
                    )
                } else {
                    onSuccess(null)
                }
            },
            { error ->
                Log.e("MedicationDetails", "Failed to fetch user attributes: ${error.message}")
                onSuccess(null)
            }
        )
    }

    // Helper function to get the selected days of the week as a List of Strings
    private fun getSelectedDaysOfWeek(): List<String> {
        val selectedDays = mutableListOf<String>()
        if (mondayCheckbox.isChecked) selectedDays.add("Monday")
        if (tuesdayCheckbox.isChecked) selectedDays.add("Tuesday")
        if (wednesdayCheckbox.isChecked) selectedDays.add("Wednesday")
        if (thursdayCheckbox.isChecked) selectedDays.add("Thursday")
        if (fridayCheckbox.isChecked) selectedDays.add("Friday")
        if (saturdayCheckbox.isChecked) selectedDays.add("Saturday")
        if (sundayCheckbox.isChecked) selectedDays.add("Sunday")

        return selectedDays
    }
}


//class MedicationDetailsFragment : Fragment() {
//
//    private lateinit var medicationName: TextView
//    private lateinit var medicationCompany: TextView
//    private lateinit var medicationDIN: TextView
//    private lateinit var dosageInput: EditText
//    private lateinit var formInput: EditText
//    private lateinit var startDateButton: Button
//    private lateinit var endDateButton: Button
//    private lateinit var timeButton: Button
//    private lateinit var saveButton: Button
//    private lateinit var cancelButton: Button
//    private lateinit var mondayCheckbox: CheckBox
//    private lateinit var tuesdayCheckbox: CheckBox
//    private lateinit var wednesdayCheckbox: CheckBox
//    private lateinit var thursdayCheckbox: CheckBox
//    private lateinit var fridayCheckbox: CheckBox
//    private lateinit var saturdayCheckbox: CheckBox
//    private lateinit var sundayCheckbox: CheckBox
//
//    private var selectedStartDate: String = ""
//    private var selectedEndDate: String = ""
//    private var selectedTime: String = ""
//
//    // Declare args variable to store the Safe Args
//    private lateinit var args: MedicationDetailsFragmentArgs
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_medication_details, container, false)
//
//        // Retrieve the Safe Args
//        args = MedicationDetailsFragmentArgs.fromBundle(requireArguments())
//
//
//        // Initialize UI elements
//        medicationName = view.findViewById(R.id.medication_name)
//        medicationCompany = view.findViewById(R.id.medication_company)
//        medicationDIN = view.findViewById(R.id.medication_din)
//        dosageInput = view.findViewById(R.id.dosage_input)
//        formInput = view.findViewById(R.id.form_input)
//        startDateButton = view.findViewById(R.id.select_start_date)
//        endDateButton = view.findViewById(R.id.select_end_date)
//        timeButton = view.findViewById(R.id.select_time_button)
//        saveButton = view.findViewById(R.id.save_medication_button)
//        cancelButton = view.findViewById(R.id.cancel_button)
//        mondayCheckbox = view.findViewById(R.id.checkbox_monday)
//        tuesdayCheckbox = view.findViewById(R.id.checkbox_tuesday)
//        wednesdayCheckbox = view.findViewById(R.id.checkbox_wednesday)
//        thursdayCheckbox = view.findViewById(R.id.checkbox_thursday)
//        fridayCheckbox = view.findViewById(R.id.checkbox_friday)
//        saturdayCheckbox = view.findViewById(R.id.checkbox_saturday)
//        sundayCheckbox = view.findViewById(R.id.checkbox_sunday)
//
//        // Retrieve the Safe Args
//        val args = MedicationDetailsFragmentArgs.fromBundle(requireArguments())
//        medicationName.text = args.medicationName
//        medicationCompany.text = args.medicationCompany
//        medicationDIN.text = "DIN: ${args.medicationDIN}"
//
//        // Handle cancel button click
//        cancelButton.setOnClickListener {
//            findNavController().navigateUp()
//        }
//
//        // Set up start date picker
//        startDateButton.setOnClickListener {
//            showDatePicker { date ->
//                selectedStartDate = date
//                startDateButton.text = "Start Date: $date"
//            }
//        }
//
//        // Set up end date picker
//        endDateButton.setOnClickListener {
//            showDatePicker { date ->
//                selectedEndDate = date
//                endDateButton.text = "End Date: $date"
//            }
//        }
//
//        // Set up time picker
//        timeButton.setOnClickListener {
//            showTimePicker { time ->
//                selectedTime = time
//                timeButton.text = "Time: $time"
//            }
//        }
//
//        // Save button click handler
//        saveButton.setOnClickListener {
//            saveMedicationDetails()
//        }
//
//        return view
//    }
//
//    // Function to show date picker
//    private fun showDatePicker(onDateSelected: (String) -> Unit) {
//        val calendar = Calendar.getInstance()
//        val datePickerDialog = DatePickerDialog(
//            requireContext(),
//            { _, year, month, dayOfMonth ->
//                val selectedDate = "$dayOfMonth/${month + 1}/$year"
//                onDateSelected(selectedDate)
//            },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//        datePickerDialog.show()
//    }
//
//    // Function to show time picker
//    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
//        val calendar = Calendar.getInstance()
//        val timePickerDialog = TimePickerDialog(
//            requireContext(),
//            { _, hourOfDay, minute ->
//                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
//                onTimeSelected(selectedTime)
//            },
//            calendar.get(Calendar.HOUR_OF_DAY),
//            calendar.get(Calendar.MINUTE),
//            true
//        )
//        timePickerDialog.show()
//    }
//
//    // Function to save the medication details
//    private fun saveMedicationDetails() {
//        val dosage = dosageInput.text.toString()
//        val form = formInput.text.toString()
//        val daysOfWeek = getSelectedDaysOfWeek()
//
//        if (dosage.isEmpty() || form.isEmpty()) {
//            Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Fetch the current user's profile ID
//        getCurrentUserProfileId { userProfileId ->
//            if (userProfileId != null) {
//                // Save the medication for the user
//                val userMedication = UserMedication.builder()
//                    .userProfileId(userProfileId)
//                    .medicationId(args.medicationDIN)
//                    .dosage(dosage)
//                    .form(form)
//                    .startDate(selectedStartDate)
//                    .endDate(selectedEndDate)
//                    .daysOfWeek(daysOfWeek)
//                    .time(selectedTime)
//                    .build()
//
//                Amplify.API.mutate(
//                    ModelMutation.create(userMedication),
//                    { response ->
//                        requireActivity().runOnUiThread {
//                            Toast.makeText(requireContext(), "Medication saved successfully", Toast.LENGTH_LONG).show()
//                            findNavController().navigateUp()
//                        }
//                    },
//                    { error ->
//                        requireActivity().runOnUiThread {
//                            Log.e("MedicationDetails", "Failed to save medication: ${error.message}")
//                            Toast.makeText(requireContext(), "Failed to save medication", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                )
//            } else {
//                requireActivity().runOnUiThread {
//                    Toast.makeText(requireContext(), "Failed to fetch user profile", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//
//    // Function to retrieve the current user's profile ID
//    private fun getCurrentUserProfileId(onSuccess: (String?) -> Unit) {
//        Amplify.Auth.fetchUserAttributes(
//            { attributes ->
//                val email = attributes.find { it.key.keyString == "email" }?.value
//                if (email != null) {
//                    // Use ModelQuery.list with a filter to search by email
//                    val emailPredicate = UserProfile.EMAIL.eq(email)
//                    Amplify.API.query(
//                        ModelQuery.list(UserProfile::class.java, emailPredicate),
//                        { response ->
//                            val userProfile = response.data?.items?.firstOrNull()
//                            val userId = userProfile?.id
//                            onSuccess(userId)
//                        },
//                        { error ->
//                            Log.e("MedicationDetails", "Failed to fetch user profile: ${error.message}")
//                            onSuccess(null)
//                        }
//                    )
//                } else {
//                    onSuccess(null)
//                }
//            },
//            { error ->
//                Log.e("MedicationDetails", "Failed to fetch user attributes: ${error.message}")
//                onSuccess(null)
//            }
//        )
//    }
//
//
//    // Helper function to get the selected days of the week
//    // Helper function to get the selected days of the week as a List of Strings
//    private fun getSelectedDaysOfWeek(): List<String> {
//        val selectedDays = mutableListOf<String>()
//        if (mondayCheckbox.isChecked) selectedDays.add("Monday")
//        if (tuesdayCheckbox.isChecked) selectedDays.add("Tuesday")
//        if (wednesdayCheckbox.isChecked) selectedDays.add("Wednesday")
//        if (thursdayCheckbox.isChecked) selectedDays.add("Thursday")
//        if (fridayCheckbox.isChecked) selectedDays.add("Friday")
//        if (saturdayCheckbox.isChecked) selectedDays.add("Saturday")
//        if (sundayCheckbox.isChecked) selectedDays.add("Sunday")
//
//        return selectedDays
//    }
//
//}
