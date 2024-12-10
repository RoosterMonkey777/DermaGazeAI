package khan.z.dermagazeai.topmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import khan.z.dermagazeai.R

class FaqAdapter(private val faqList: List<FaqItem>) :
    RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {

    inner class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.faq_question)
        val answerTextView: TextView = itemView.findViewById(R.id.faq_answer)
        val faqIcon: ImageView = itemView.findViewById(R.id.faq_icon)
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

        // Toggle the icon and description based on the expanded/collapsed state
        holder.faqIcon.setImageResource(
            if (faqItem.isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        )
        holder.faqIcon.contentDescription = holder.itemView.context.getString(
            if (faqItem.isExpanded) R.string.collapse_description else R.string.expand_description
        )

        // Handle item click to expand/collapse and update the icon
        holder.itemView.setOnClickListener {
            faqItem.isExpanded = !faqItem.isExpanded
            notifyItemChanged(position)
        }
    }


    override fun getItemCount(): Int = faqList.size
}

