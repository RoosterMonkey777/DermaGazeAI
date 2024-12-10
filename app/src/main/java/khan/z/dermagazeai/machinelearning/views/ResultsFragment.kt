package khan.z.dermagazeai.machinelearning.views

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.SkinAssessment
import com.amplifyframework.datastore.generated.model.UserProfile
import khan.z.dermagazeai.R
import java.util.*
import java.util.Date

class ResultsFragment : Fragment() {

    private lateinit var imgResultPreview: ImageView
    private lateinit var tvCondition: TextView
    private lateinit var tvSeverity: TextView
    private lateinit var probabilityBar: ProgressBar
    private lateinit var tvProbabilityValue: TextView
    private lateinit var backButton: ImageButton
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)

        // Bind UI elements
        imgResultPreview = view.findViewById(R.id.imgResultPreview)
        tvCondition = view.findViewById(R.id.tvCondition)
        tvSeverity = view.findViewById(R.id.tvSeverity)
        probabilityBar = view.findViewById(R.id.probabilityBar)
        tvProbabilityValue = view.findViewById(R.id.tvProbabilityValue)
        backButton = view.findViewById(R.id.back_button)
        btnSave = view.findViewById(R.id.btnSave)

        // Set up the back button click listener
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Retrieve arguments and set data
        val args = ResultsFragmentArgs.fromBundle(requireArguments())
        tvCondition.text = "Condition: ${args.condition}"
        tvSeverity.text = "Severity: ${args.severity}"
        probabilityBar.progress = args.probability
        tvProbabilityValue.text = "${args.probability}%" // Display percentage
        imgResultPreview.setImageURI(Uri.parse(args.imageUri))

        // Save button click listener
        btnSave.setOnClickListener {
            getCurrentUserProfileId { userProfileId ->
                if (userProfileId != null) {
                    saveSkinAssessment(userProfileId, args.imageUri, args.condition, args.severity, args.probability)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch user profile", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    // Function to save the skin assessment
    private fun saveSkinAssessment(userProfileId: String, imageUri: String, condition: String, severity: String, probability: Int) {
        val assessment = SkinAssessment.builder()
            .userProfileId(userProfileId)
            .imageUri(imageUri)
            .condition(condition)
            .severity(severity)
            .probability(probability)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(assessment),
            { response ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Assessment saved successfully!", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Failed to save assessment: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Function to retrieve the current user's profile ID
    private fun getCurrentUserProfileId(onSuccess: (String?) -> Unit) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.find { it.key.keyString == "email" }?.value
                if (email != null) {
                    val emailPredicate = UserProfile.EMAIL.eq(email)
                    Amplify.API.query(
                        ModelQuery.list(UserProfile::class.java, emailPredicate),
                        { response ->
                            val userProfile = response.data?.items?.firstOrNull()
                            val userId = userProfile?.id
                            onSuccess(userId)
                        },
                        { error ->
                            onSuccess(null)
                        }
                    )
                } else {
                    onSuccess(null)
                }
            },
            { error ->
                onSuccess(null)
            }
        )
    }
}



//import android.net.Uri
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.ImageView
//import android.widget.ProgressBar
//import android.widget.TextView
//import khan.z.dermagazeai.R
//
//import androidx.navigation.fragment.findNavController
//
//
//class ResultsFragment : Fragment() {
//
//    private lateinit var imgResultPreview: ImageView
//    private lateinit var tvCondition: TextView
//    private lateinit var tvSeverity: TextView
//    private lateinit var probabilityBar: ProgressBar
//    private lateinit var backButton: ImageButton
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_results, container, false)
//
//        // Bind UI elements
//        imgResultPreview = view.findViewById(R.id.imgResultPreview)
//        tvCondition = view.findViewById(R.id.tvCondition)
//        tvSeverity = view.findViewById(R.id.tvSeverity)
//        probabilityBar = view.findViewById(R.id.probabilityBar)
//        backButton = view.findViewById(R.id.back_button)
//
//        // Set up the back button click listener
//        backButton.setOnClickListener {
//            findNavController().navigateUp()
//        }
//
//        // Retrieve arguments and set data
//        val args = ResultsFragmentArgs.fromBundle(requireArguments())
//        tvCondition.text = "Condition: ${args.condition}"
//        tvSeverity.text = "Severity: ${args.severity}"
//        probabilityBar.progress = args.probability
//
//        // Display the selected image
//        imgResultPreview.setImageURI(Uri.parse(args.imageUri))
//
//        return view
//    }
//}