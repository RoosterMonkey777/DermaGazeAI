package khan.z.dermagazeai.machinelearning.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.SkinAssessment
import khan.z.dermagazeai.R

class SkinAssessmentAdapter(private val assessments: List<SkinAssessment>) :
    RecyclerView.Adapter<SkinAssessmentAdapter.SkinAssessmentViewHolder>() {

    class SkinAssessmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val assessmentImage: ImageView = view.findViewById(R.id.assessment_image)
        val assessmentCondition: TextView = view.findViewById(R.id.assessment_condition)
        val assessmentSeverity: TextView = view.findViewById(R.id.assessment_severity)
        val assessmentProbability: TextView = view.findViewById(R.id.assessment_probability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkinAssessmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skin_assessment_card, parent, false)
        return SkinAssessmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkinAssessmentViewHolder, position: Int) {
        val assessment = assessments[position]
        holder.assessmentCondition.text = "Condition: ${assessment.condition}"
        holder.assessmentSeverity.text = "Severity: ${assessment.severity}"
        holder.assessmentProbability.text = "Probability: ${assessment.probability}%"

        // Set the image (assuming imageUri is a local URI or can be loaded with an image library)
        // Use Glide or Picasso to load images if needed
        // Glide.with(holder.assessmentImage.context).load(assessment.imageUri).into(holder.assessmentImage)
    }

    override fun getItemCount() = assessments.size
}