package khan.z.dermagazeai.medication.views

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.UserMedication
import com.amplifyframework.datastore.generated.model.UserProfile
import com.google.android.material.floatingactionbutton.FloatingActionButton
import khan.z.dermagazeai.R


//class MedicationFragment : Fragment() {
//
//    private lateinit var medicationRecyclerView: RecyclerView
//    private lateinit var noMedicationsMessage: TextView
//    private lateinit var addMedicationButton: FloatingActionButton
//    private lateinit var medicationAdapter: MedicationAdapter
//    private var medications: MutableList<UserMedication> = mutableListOf() // Change to MutableList for deletion
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_medication, container, false)
//
//        // Initialize UI elements
//        medicationRecyclerView = view.findViewById(R.id.medication_recycler_view)
//        noMedicationsMessage = view.findViewById(R.id.no_medications_message)
//        addMedicationButton = view.findViewById(R.id.add_medication_button)
//
//        // Set up RecyclerView
//        medicationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        medicationAdapter = MedicationAdapter(medications)
//        medicationRecyclerView.adapter = medicationAdapter
//
//        // Set up swipe to delete
//        setUpSwipeToDelete()
//
//        // Handle adding medication button click to navigate to SearchMedicationFragment
//        addMedicationButton.setOnClickListener {
//            navigateToSearchMedicationFragment()
//        }
//
//        // Fetch and display user medications
//        fetchUserMedications()
//
//        return view
//    }
//
//    private fun setUpSwipeToDelete() {
//        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false // We are not interested in moving items
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                val userMedication = medications[position]
//
//                // Remove from local list
//                medications.removeAt(position)
//                medicationAdapter.notifyItemRemoved(position)
//
//                // Delete from database
//                deleteUserMedication(userMedication)
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                // Customize the background and icon
//                val icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_50) // Use your delete icon
//                val background = ColorDrawable(Color.RED)
//
//                val itemView = viewHolder.itemView
//                val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
//
//                if (dX > 0) { // Swiping to the right
//                    // Set the background color
//                    background.setBounds(
//                        itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom
//                    )
//                    background.draw(c)
//
//                    // Calculate position of delete icon
//                    val iconLeft = itemView.left + iconMargin
//                    val iconRight = iconLeft + icon.intrinsicWidth
//                    val iconTop = itemView.top + iconMargin
//                    val iconBottom = iconTop + icon.intrinsicHeight
//
//                    // Draw delete icon
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//                    icon.draw(c)
//                }
//
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(swipeHandler)
//        itemTouchHelper.attachToRecyclerView(medicationRecyclerView)
//    }
//
//    private fun deleteUserMedication(userMedication: UserMedication) {
//        Amplify.API.mutate(
//            ModelMutation.delete(userMedication),
//            { response ->
//                requireActivity().runOnUiThread {
//                    Toast.makeText(requireContext(), "Medication deleted successfully", Toast.LENGTH_SHORT).show()
//
//                    // If there are no more medications, show the empty message
//                    if (medications.isEmpty()) {
//                        noMedicationsMessage.visibility = View.VISIBLE
//                    }
//                }
//            },
//            { error ->
//                requireActivity().runOnUiThread {
//                    Toast.makeText(requireContext(), "Failed to delete medication: ${error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//    }
//
//    private fun fetchUserMedications() {
//        getCurrentUserProfileId { userProfileId ->
//            if (userProfileId != null) {
//                val userProfileIdPredicate = UserMedication.USER_PROFILE_ID.eq(userProfileId)
//                Amplify.API.query(
//                    ModelQuery.list(UserMedication::class.java, userProfileIdPredicate),
//                    { response ->
//                        val userMedications = response.data?.items?.toList() ?: emptyList()
//
//                        requireActivity().runOnUiThread {
//                            if (userMedications.isEmpty()) {
//                                noMedicationsMessage.visibility = View.VISIBLE
//                            } else {
//                                noMedicationsMessage.visibility = View.GONE
//                                medications.clear()
//                                medications.addAll(userMedications)
//                                medicationAdapter.notifyDataSetChanged()
//                            }
//                        }
//                    },
//                    { error ->
//                        requireActivity().runOnUiThread {
//                            Log.e("MedicationFragment", "Error fetching medications: ${error.message}")
//                        }
//                    }
//                )
//            }
//        }
//    }
//
//    // Function to navigate to the SearchMedicationFragment
//    private fun navigateToSearchMedicationFragment() {
//        findNavController().navigate(R.id.action_medicationFragment_to_searchMedicationFragment)
//    }
//
//    // Function to retrieve the current user's profile ID
//    private fun getCurrentUserProfileId(onSuccess: (String?) -> Unit) {
//        Amplify.Auth.fetchUserAttributes(
//            { attributes ->
//                val email = attributes.find { it.key.keyString == "email" }?.value
//                if (email != null) {
//                    // Use ModelQuery.list with a filter to search by email
//                    val emailPredicate = UserProfile.EMAIL.eq(email)
//                    Amplify.API.query(
//                        ModelQuery.list(UserProfile::class.java, emailPredicate),
//                        { response ->
//                            val userProfile = response.data?.items?.firstOrNull()
//                            val userId = userProfile?.id
//                            onSuccess(userId)
//                        },
//                        { error ->
//                            Log.e("MedicationFragment", "Failed to fetch user profile: ${error.message}")
//                            onSuccess(null)
//                        }
//                    )
//                } else {
//                    onSuccess(null)
//                }
//            },
//            { error ->
//                Log.e("MedicationFragment", "Failed to fetch user attributes: ${error.message}")
//                onSuccess(null)
//            }
//        )
//    }
//}

class MedicationFragment : Fragment() {

    private lateinit var medicationRecyclerView: RecyclerView
    private lateinit var noMedicationsMessage: TextView
    private lateinit var addMedicationButton: FloatingActionButton
    private lateinit var medicationAdapter: MedicationAdapter
    private var medications: MutableList<UserMedication> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medication, container, false)

        // Initialize UI elements
        medicationRecyclerView = view.findViewById(R.id.medication_recycler_view)
        noMedicationsMessage = view.findViewById(R.id.no_medications_message)
        addMedicationButton = view.findViewById(R.id.add_medication_button)

        // Set up RecyclerView
        medicationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        medicationAdapter = MedicationAdapter(medications) { medication ->
            navigateToMedicationDetails(medication)
        }
        medicationRecyclerView.adapter = medicationAdapter

        // Set up swipe to delete
        setUpSwipeToDelete()

        // Handle adding medication button click to navigate to SearchMedicationFragment
        addMedicationButton.setOnClickListener {
            navigateToSearchMedicationFragment()
        }

        // Fetch and display user medications
        fetchUserMedications()

        return view
    }

    private fun setUpSwipeToDelete() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // We are not interested in moving items
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val userMedication = medications[position]

                // Remove from local list
                medications.removeAt(position)
                medicationAdapter.notifyItemRemoved(position)

                // Delete from database
                deleteUserMedication(userMedication)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // Customize the background and icon
                val icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_delete_50) // Use your delete icon
                val background = ColorDrawable(Color.RED)

                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2

                if (dX > 0) { // Swiping to the right
                    // Set the background color
                    background.setBounds(
                        itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom
                    )
                    background.draw(c)

                    // Calculate position of delete icon
                    val iconLeft = itemView.left + iconMargin
                    val iconRight = iconLeft + icon.intrinsicWidth
                    val iconTop = itemView.top + iconMargin
                    val iconBottom = iconTop + icon.intrinsicHeight

                    // Draw delete icon
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(medicationRecyclerView)
    }

    private fun deleteUserMedication(userMedication: UserMedication) {
        Amplify.API.mutate(
            ModelMutation.delete(userMedication),
            { response ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Medication deleted successfully", Toast.LENGTH_SHORT).show()

                    // If there are no more medications, show the empty message
                    if (medications.isEmpty()) {
                        noMedicationsMessage.visibility = View.VISIBLE
                    }
                }
            },
            { error ->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Failed to delete medication: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun fetchUserMedications() {
        getCurrentUserProfileId { userProfileId ->
            if (userProfileId != null) {
                val userProfileIdPredicate = UserMedication.USER_PROFILE_ID.eq(userProfileId)
                Amplify.API.query(
                    ModelQuery.list(UserMedication::class.java, userProfileIdPredicate),
                    { response ->
                        val userMedications = response.data?.items?.toList() ?: emptyList()

                        requireActivity().runOnUiThread {
                            if (userMedications.isEmpty()) {
                                noMedicationsMessage.visibility = View.VISIBLE
                            } else {
                                noMedicationsMessage.visibility = View.GONE
                                medications.clear()
                                medications.addAll(userMedications)
                                medicationAdapter.notifyDataSetChanged()
                            }
                        }
                    },
                    { error ->
                        requireActivity().runOnUiThread {
                            Log.e("MedicationFragment", "Error fetching medications: ${error.message}")
                        }
                    }
                )
            }
        }
    }

    private fun navigateToMedicationDetails(medication: UserMedication) {
        val action = MedicationFragmentDirections
            .actionMedicationFragmentToUserMedicationDetailsFragment(
                medicationId = medication.medicationId,
                dosage = medication.dosage,
                form = medication.form,
                startDate = medication.startDate,
                endDate = medication.endDate,
                medicationName = medication.medicationName,  // Pass the medication name here
                medicationCompany = medication.companyName  // Pass the medication company here
            )
        findNavController().navigate(action)
    }

    // Function to navigate to the SearchMedicationFragment
    private fun navigateToSearchMedicationFragment() {
        findNavController().navigate(R.id.action_medicationFragment_to_searchMedicationFragment)
    }

    // Function to retrieve the current user's profile ID
    private fun getCurrentUserProfileId(onSuccess: (String?) -> Unit) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.find { it.key.keyString == "email" }?.value
                if (email != null) {
                    // Use ModelQuery.list with a filter to search by email
                    val emailPredicate = UserProfile.EMAIL.eq(email)
                    Amplify.API.query(
                        ModelQuery.list(UserProfile::class.java, emailPredicate),
                        { response ->
                            val userProfile = response.data?.items?.firstOrNull()
                            val userId = userProfile?.id
                            onSuccess(userId)
                        },
                        { error ->
                            Log.e("MedicationFragment", "Failed to fetch user profile: ${error.message}")
                            onSuccess(null)
                        }
                    )
                } else {
                    onSuccess(null)
                }
            },
            { error ->
                Log.e("MedicationFragment", "Failed to fetch user attributes: ${error.message}")
                onSuccess(null)
            }
        )
    }
}





