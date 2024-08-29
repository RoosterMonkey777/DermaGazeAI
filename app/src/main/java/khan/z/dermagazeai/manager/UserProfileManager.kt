package khan.z.dermagazeai.manager

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.ProductCategory
import com.amplifyframework.datastore.generated.model.SkinProblem
import com.amplifyframework.datastore.generated.model.SkinType
import com.amplifyframework.datastore.generated.model.User
import com.amplifyframework.datastore.generated.model.SkinCareProduct
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class UserProfileManager {

    // CRUD Operations

    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        age: Int?,
        gender: String,
        skinType: String,
        productType: String,
        skinProblems: List<String>,
        notableEffects: List<String>,
        consentGiven: Boolean,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val user = User.builder()
            .firstname(firstName)
            .lastname(lastName)
            .email(email)
            .consentGiven(consentGiven)
            .age(age)
            .gender(gender)
            .skintype(skinType)
            .productType(productType)
            .skinProblems(skinProblems)
            .notableEffects(notableEffects)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            { response ->
                if (response.hasErrors()) {
                    onError(Exception(response.errors.first().message))
                } else {
                    onSuccess()
                }
            },
            { error -> onError(error) }
        )
    }

    fun updateUser(
        user: User,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        Amplify.API.mutate(
            ModelMutation.update(user),
            { response ->
                if (response.hasErrors()) {
                    onError(Exception(response.errors.first().message))
                } else {
                    onSuccess()
                }
            },
            { error -> onError(error) }
        )
    }




    fun fetchUserEmail(onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val email = attributes.firstOrNull { it.key.keyString == "email" }?.value
                if (email != null) {
                    onSuccess(email)
                } else {
                    onError(Exception("Email not found in user attributes"))
                }
            },
            { error -> onError(error) }
        )
    }

    fun checkUserProfile(email: String, onProfileFound: (User) -> Unit, onProfileNotFound: () -> Unit, onError: (Exception) -> Unit) {
        Amplify.API.query(
            ModelQuery.list(User::class.java, User.EMAIL.eq(email)),
            { response ->
                val items = response.data?.items
                if (items != null && items.iterator().hasNext()) {
                    onProfileFound(items.first())
                } else {
                    onProfileNotFound()
                }
            },
            { error -> onError(error) }
        )
    }


    // UI updates

    fun populateSkinTypeSpinner(context: Context, spinner: Spinner) {
        val skinTypes = listOf("Normal", "Dry", "Oily", "Combination", "Sensitive")
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, skinTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun populateProductCategorySpinner(context: Context, spinner: Spinner) {
        val productCategories = listOf("Face Wash", "Toner", "Serum", "Moisturizer", "Sunscreen")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, productCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun populateSkinProblemsChipGroup(context: Context, chipGroup: ChipGroup, selectedProblems: List<String>?) {
        val skinProblems = listOf(
            "Dull Skin", "Acne", "Acne Scars", "Large Pores", "Blackheads",
            "Uneven Skin Tone", "Redness", "Fine Lines and Wrinkles", "Sagging Skin", "Dark Spots"
        )
        chipGroup.removeAllViews()
        skinProblems.forEach { problem ->
            val chip = Chip(context)
            chip.text = problem
            chip.isCheckable = true
            if (selectedProblems?.contains(problem) == true) {
                chip.isChecked = true
            }
            chipGroup.addView(chip)
        }
    }

    fun updateNotableEffects(
        selectedSkinType: String?,
        selectedProductType: String?,
        chipGroup: ChipGroup,
        context: Context
    ) {
        if (selectedSkinType == null || selectedProductType == null) {
            Log.e("UserProfileManager", "Skin type or Product type not selected.")
            return
        }

        Amplify.API.query(
            ModelQuery.list(
                SkinCareProduct::class.java,
                SkinCareProduct.SKINTYPE.contains(selectedSkinType)
                    .and(SkinCareProduct.PRODUCT_TYPE.eq(selectedProductType))
            ),
            { response ->
                val items = response.data?.items
                if (items != null && items.iterator().hasNext()) {
                    val notableEffects = items.flatMap { it.notableEffects ?: emptyList() }.distinct()
                    (context as Activity).runOnUiThread {
                        chipGroup.removeAllViews()
                        notableEffects.forEach { effect ->
                            val chip = Chip(context)
                            chip.text = effect
                            chip.isCheckable = true
                            chipGroup.addView(chip)
                        }
                    }
                } else {
                    Log.w("UserProfileManager", "No matching products found.")
                }
            },
            { error ->
                Log.e("UserProfileManager", "Failed to fetch notable effects", error)
            }
        )
    }


}
