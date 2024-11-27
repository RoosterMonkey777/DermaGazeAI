package khan.z.dermagazeai.medication.views

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Medication
import com.amplifyframework.datastore.generated.model.UserMedication
import khan.z.dermagazeai.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MedicationAdapter(
    private val medications: List<UserMedication>,
    private val onItemClick: (UserMedication) -> Unit // Added click listener
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.medication_card_item, parent, false)
        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medication = medications[position]
        holder.bind(medication)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onItemClick(medication) // Trigger the click listener with the selected medication
        }
    }

    override fun getItemCount(): Int = medications.size

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val medicationNameTextView: TextView = itemView.findViewById(R.id.medication_name)
        private val dosageTextView: TextView = itemView.findViewById(R.id.medication_dosage)
        private val companyTextView: TextView = itemView.findViewById(R.id.medication_company)
        private val nextMedicationTimeTextView: TextView = itemView.findViewById(R.id.next_medication_time)

        fun bind(medication: UserMedication) {
            medicationNameTextView.text = medication.medicationName // Assuming this is medication name
            companyTextView.text = medication.companyName
            dosageTextView.text = "Dosage: ${medication.dosage}"

            // Calculate the next medication time and display it
            val nextTime = calculateNextMedicationTime(medication)
            nextMedicationTimeTextView.text = "Next: $nextTime"
        }

        private fun calculateNextMedicationTime(medication: UserMedication): String {
            val daysOfWeek = medication.daysOfWeek ?: emptyList()
            if (daysOfWeek.isEmpty()) {
                Log.e("MedicationAdapter", "Days of the week are not specified for medication: ${medication.medicationName}")
                return "No upcoming doses"
            }

            val medicationTime = medication.time ?: "08:00" // Default time if not set

            // Parse startDate and endDate
            val startDate = medication.startDate?.let { parseDate(it) } ?: Date()
            val endDate = medication.endDate?.let { parseDate(it) }

            val currentDateTime = Calendar.getInstance().time

            // If end date is provided, check if the medication period has ended
            if (endDate != null && currentDateTime.after(endDate)) {
                return "No upcoming doses" // Medication period ended
            }

            // Get the next valid day of the week
            val nextValidDate = getNextValidDate(daysOfWeek, medicationTime, startDate, endDate)
            return nextValidDate?.let { formatDateTime(it) } ?: "No upcoming doses"
        }

        private fun getNextValidDate(
            daysOfWeek: List<String>,
            medicationTime: String,
            startDate: Date,
            endDate: Date?
        ): Date? {
            val calendar = Calendar.getInstance()

            // Start from today's date or the medication start date, whichever is later
            if (calendar.time.before(startDate)) {
                calendar.time = startDate
            }

            // Validate and parse medicationTime
            val medicationTimeParts = medicationTime.split(":").mapNotNull { part ->
                part.toIntOrNull() // Safely parse each part, return null if invalid
            }

            if (medicationTimeParts.size != 2) {
                // Log error and return null if time format is invalid
                Log.e("MedicationAdapter", "Invalid medication time format: $medicationTime")
                return null
            }

            val medicationHour = medicationTimeParts[0]
            val medicationMinute = medicationTimeParts[1]

            for (i in 0..6) { // Check the next 7 days
                val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                val dayOfWeekString = dayOfWeekToString(currentDayOfWeek)

                if (daysOfWeek.contains(dayOfWeekString)) {
                    // Set the time for that day
                    calendar.set(Calendar.HOUR_OF_DAY, medicationHour)
                    calendar.set(Calendar.MINUTE, medicationMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    // If this date is valid, return it
                    val nextDate = calendar.time
                    if ((endDate == null || nextDate.before(endDate)) && nextDate.after(Date())) {
                        return nextDate
                    }
                }

                // Move to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            return null // No valid days found within the period
        }


        private fun dayOfWeekToString(dayOfWeek: Int): String {
            return when (dayOfWeek) {
                Calendar.SUNDAY -> "Sunday"
                Calendar.MONDAY -> "Monday"
                Calendar.TUESDAY -> "Tuesday"
                Calendar.WEDNESDAY -> "Wednesday"
                Calendar.THURSDAY -> "Thursday"
                Calendar.FRIDAY -> "Friday"
                Calendar.SATURDAY -> "Saturday"
                else -> ""
            }
        }

        private fun parseDate(dateString: String): Date {
            val format = SimpleDateFormat("d/M/yyyy", Locale.getDefault()) // Use the correct format
            return format.parse(dateString) ?: Date()
        }

        private fun formatDateTime(date: Date): String {
            val format = SimpleDateFormat("EEE, MMM d 'at' h:mm a", Locale.getDefault())
            return format.format(date)
        }
    }
}

