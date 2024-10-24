package khan.z.dermagazeai.topmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import khan.z.dermagazeai.R

class FaqAdapter(private val faqList: List<String>) :
    RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.faq_question)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faq_item, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.questionTextView.text = faqList[position]

        // Set click listener to navigate to the detailed FAQ section
        holder.itemView.setOnClickListener {
            // Handle the click, potentially navigate to a detailed FAQ fragment
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }
}
