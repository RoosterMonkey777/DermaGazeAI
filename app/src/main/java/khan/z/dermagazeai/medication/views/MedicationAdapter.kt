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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.UserMedication
import khan.z.dermagazeai.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MedicationAdapter(
    private val medications: List<UserMedication>,
    private val onItemClick: (UserMedication) -> Unit // Click listener for medication item
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
            onItemClick(medication)
        }
    }

    override fun getItemCount(): Int = medications.size

    inner class MedicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val medicationNameTextView: TextView = itemView.findViewById(R.id.medication_name)
        private val dosageTextView: TextView = itemView.findViewById(R.id.medication_dosage)
        private val companyTextView: TextView = itemView.findViewById(R.id.medication_company)
        private val nextMedicationTimeTextView: TextView = itemView.findViewById(R.id.next_medication_time)

        fun bind(medication: UserMedication) {
            medicationNameTextView.text = medication.medicationName
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

            val startDate = medication.startDate?.let { parseDate(it) } ?: Date()
            val endDate = medication.endDate?.let { parseDate(it) }

            val currentDateTime = Calendar.getInstance().time

            if (endDate != null && currentDateTime.after(endDate)) {
                return "No upcoming doses" // Medication period ended
            }

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

            if (calendar.time.before(startDate)) {
                calendar.time = startDate
            }

            val medicationTimeParts = medicationTime.split(":").map { it.toInt() }
            val medicationHour = medicationTimeParts[0]
            val medicationMinute = medicationTimeParts[1]

            for (i in 0..6) { // Check the next 7 days
                val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                val dayOfWeekString = dayOfWeekToString(currentDayOfWeek)

                if (daysOfWeek.contains(dayOfWeekString)) {
                    calendar.set(Calendar.HOUR_OF_DAY, medicationHour)
                    calendar.set(Calendar.MINUTE, medicationMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    val nextDate = calendar.time
                    if ((endDate == null || nextDate.before(endDate)) && nextDate.after(Date())) {
                        return nextDate
                    }
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            return null
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
            val format = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
            return format.parse(dateString) ?: Date()
        }

        private fun formatDateTime(date: Date): String {
            val format = SimpleDateFormat("EEE, MMM d 'at' h:mm a", Locale.getDefault())
            return format.format(date)
        }
    }

    fun scheduleMedicationAlarm(context: Context, medication: UserMedication, triggerTime: Calendar) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, MedicationDetailsFragment::class.java).apply {
                putExtra("medication_name", medication.medicationName)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                medication.medicationName.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime.timeInMillis,
                pendingIntent
            )

            Toast.makeText(context, "Alarm set for ${medication.medicationName}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MedicationAdapter", "Failed to set alarm: ${e.message}")
            Toast.makeText(context, "Failed to set alarm: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
