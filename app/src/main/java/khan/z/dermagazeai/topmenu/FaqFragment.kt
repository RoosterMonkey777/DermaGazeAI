package khan.z.dermagazeai.topmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import khan.z.dermagazeai.R


class FaqFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFaq)

        val faqList = listOf(
            FaqItem("What is DermaGazeAI?", "DermaGazeAI is an AI-powered application designed to analyze images of skin conditions such as acne, eczema, and melanoma. The app uses machine learning algorithms to provide users with an initial assessment of their skin condition based on the photos they upload."),
            FaqItem("Who should use DermaGazeAI?", "DermaGazeAI is ideal for individuals who want to monitor skin conditions or are concerned about changes in their skin. While the app is useful for anyone who wants a preliminary analysis, it’s particularly helpful for users seeking guidance before visiting a dermatologist."),
            FaqItem("Does DermaGazeAI replace the Doctor?", "No, DermaGazeAI does not replace a doctor. The app provides a preliminary analysis, but it is not a substitute for professional medical advice, diagnosis, or treatment. Users should consult a healthcare professional for an official diagnosis."),
            FaqItem("What types of skin conditions can DermaGazeAI analyze?", "DermaGazeAI primarily focuses on analyzing conditions such as acne, eczema, and melanoma. As the AI develops and improves, it may expand to cover other common dermatological conditions."),
            FaqItem("How accurate is DermaGazeAI's diagnosis?", "DermaGazeAI's diagnosis is based on machine learning algorithms that are trained on large datasets of skin images. While the app provides high accuracy in identifying certain conditions, it is not 100% accurate and should not be solely relied upon for a final diagnosis."),
            FaqItem("Can I use DermaGazeAI on different parts of my body?", "Yes, you can use DermaGazeAI to analyze skin conditions on any part of your body where the skin is visible and the condition can be captured in a photo."),
            FaqItem("Can I rely on DermaGazeAI for a medical diagnosis?", "DermaGazeAI should not be used as the sole tool for medical diagnosis. While it offers helpful insights, it is essential to seek professional medical advice for an official diagnosis and treatment plan."),
            FaqItem("Is my personal information safe?", "Yes, DermaGazeAI takes privacy and data security seriously. User data, including images and personal information, are encrypted and stored securely. The app follows industry-standard security practices to ensure your information is safe."),
            FaqItem("How to use?", "To use DermaGazeAI, simply take a clear photo of the affected skin area, upload it to the app, and wait for the AI to analyze the image. The app will then provide an initial assessment based on the condition identified in the image."),
            FaqItem("How to take a photo?", "Ensure that the area of concern is well-lit and in focus. Avoid shadows or blurry images, and make sure the skin condition is clearly visible. For the best results, hold the camera steady and capture the photo as closely as possible without losing clarity."),
            FaqItem("How to save results?", "After DermaGazeAI analyzes the image and provides the results, you can save them directly within the app. The app allows you to store these results in your profile for future reference, helping you track changes over time. You can also export the results if needed for consultation with a healthcare professional."),
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = FaqAdapter(faqList)
    }

    class FaqAdapter(private val faqList: List<FaqItem>) :
        RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

        inner class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val questionTextView: TextView = itemView.findViewById(R.id.faq_question)
            val answerTextView: TextView = itemView.findViewById(R.id.faq_answer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.faq_item, parent, false)
            return FaqViewHolder(view)
        }

        override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
            val faqItem = faqList[position]
            holder.questionTextView.text = faqItem.question
            holder.answerTextView.text = faqItem.answer
            holder.answerTextView.visibility = if (faqItem.isExpanded) View.VISIBLE else View.GONE

            holder.itemView.setOnClickListener {
                faqItem.isExpanded = !faqItem.isExpanded
                notifyItemChanged(position)
            }
        }

        override fun getItemCount(): Int = faqList.size
    }
}

