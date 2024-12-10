package khan.z.dermagazeai.medication.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.SkinCareProduct
import khan.z.dermagazeai.R
import com.bumptech.glide.Glide
import android.widget.ImageView


class RecommendationPagerAdapter(
    private val products: List<SkinCareProduct>
) : RecyclerView.Adapter<RecommendationPagerAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productImageView: ImageView = view.findViewById(R.id.product_image)
    }

    // Conversion rate from IDR to CAD
    private val IDR_TO_CAD_RATE = 0.000087

    private fun convertToCAD(priceInIDR: String): String {
        val idrPrice = priceInIDR.replace("Rp", "").replace(".", "").trim().toDoubleOrNull() ?: 0.0
        val cadPrice = idrPrice * IDR_TO_CAD_RATE
        return String.format("$%.2f CAD", cadPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommended_item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.productName
        holder.productPrice.text = convertToCAD(product.price)

        Glide.with(holder.itemView.context)
            .load(product.pictureSrc)
            .error(R.drawable.baseline_image_not_supported_50) // Set fallback image
            .into(holder.productImageView)
    }

    override fun getItemCount() = products.size
}

//class RecommendationPagerAdapter(
//    private val products: List<SkinCareProduct>
//) : RecyclerView.Adapter<RecommendationPagerAdapter.ProductViewHolder>() {
//
//    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val productName: TextView = view.findViewById(R.id.product_name)
//        val productPrice: TextView = view.findViewById(R.id.product_price)
//        val productImage: ImageView = view.findViewById(R.id.product_image) // Changed from TextView to ImageView
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.recommended_item_product_card, parent, false)
//        return ProductViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//        val product = products[position]
//        holder.productName.text = product.productName
//        holder.productPrice.text = product.price
//
//        Glide.with(holder.itemView.context)
//            .load(product.pictureSrc)
//            .error(R.drawable.baseline_image_not_supported_50) // Fallback image on error
//            .into(holder.productImage)
//    }
//
//    override fun getItemCount() = products.size
//}
//
//
