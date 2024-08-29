package khan.z.dermagazeai.registration.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import khan.z.dermagazeai.R

class StartupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin: View = view.findViewById(R.id.buttonLogin)
        val buttonSignup: View = view.findViewById(R.id.buttonSignup)

        buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_startupFragment_to_loginFragment)
        }

        buttonSignup.setOnClickListener {



//            val newProduct = SkinCareProduct.builder()
//                .productName("Wardah C-Defense Energizing Creamy Wash")
//                .productType("Face Wash")
//                .brand("WARDAH")
//                .description("A mild and fresh face washer to remove any dirt, make up, and excess oil. Formulated with Vitamin C & Active Moist Complex to freshen up your face and keep it moisturize.")
//                .labels(77)
//                .price("Rp 18.900")
//                .productHref("https://www.wardahbeauty.com/en/product/skincare/c-defense-energizing-creamy-wash?ref=https://www.wardahbeauty.com/en/product/list/skincare/sort/c-defense?page=1")
//                .pictureSrc("https://wardah-mainsite.s3-ap-southeast-1.amazonaws.com/medias/products/variant-1615953407.png")
//                .notableEffects(listOf("Moisturizing"))
//                .skintype(listOf("Normal", "Combination"))
//                .skinTypes(listOf("Combination", "Normal"))
//                .build()
//
//            Amplify.API.mutate(
//                ModelMutation.create(newProduct),
//                { response: GraphQLResponse<SkinCareProduct> ->
//                    if (response.hasErrors()) {
//                        response.errors.forEach { error ->
//                            Log.e("AddProduct", "Error: ${error.message}")
//                        }
//                    } else {
//                        Log.d("AddProduct", "Added product: ${response.data?.productName}")
//                    }
//                },
//                { error -> Log.e("AddProduct", "Failed to add product", error) }
//            )

            findNavController().navigate(R.id.action_startupFragment_to_signupFragment)


        }
    }
}
