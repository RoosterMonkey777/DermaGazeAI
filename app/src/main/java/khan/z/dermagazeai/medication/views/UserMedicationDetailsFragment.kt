package khan.z.dermagazeai.medication.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.R

class UserMedicationDetailsFragment : Fragment() {

    private lateinit var backButton: ImageButton
    private lateinit var medicationNameTextView: TextView
    private lateinit var medicationCompanyTextView: TextView
    private lateinit var medicationDINTextView: TextView
    private lateinit var dosageTextView: TextView
    private lateinit var formTextView: TextView
    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_medication_details, container, false)

        // Initialize UI elements
        backButton = view.findViewById(R.id.back_button)
        medicationNameTextView = view.findViewById(R.id.medication_name)
        medicationCompanyTextView = view.findViewById(R.id.medication_company)
        medicationDINTextView = view.findViewById(R.id.medication_din)
        dosageTextView = view.findViewById(R.id.dosage)
        formTextView = view.findViewById(R.id.form)
        calendarView = view.findViewById(R.id.calendar_view)

        // Set the back button click listener to navigate back
        backButton.setOnClickListener {
            findNavController().navigateUp() // Navigate back to the previous fragment
        }

        // Set the values directly from Safe Args properties
        medicationDINTextView.text = requireArguments().getString("medicationId")
        dosageTextView.text = "Dosage: ${requireArguments().getString("dosage")}"
        formTextView.text = "Form: ${requireArguments().getString("form")}"
        medicationNameTextView.text = requireArguments().getString("medicationName")
        medicationCompanyTextView.text = requireArguments().getString("medicationCompany")

        val startDate = requireArguments().getString("startDate")
        val endDate = requireArguments().getString("endDate")

        // Handle calendar setup based on medication's schedule
        setupCalendarForMedication(startDate, endDate)

        return view
    }

    private fun setupCalendarForMedication(startDate: String?, endDate: String?) {
        // Example logic to customize calendar based on medication schedule
        // You can highlight specific dates or mark reminders
    }
}
