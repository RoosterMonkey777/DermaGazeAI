package khan.z.dermagazeai.manager

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.GraphQLRequest
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.SimpleGraphQLRequest
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.SkinCareProduct
import com.amplifyframework.datastore.generated.model.UserProfile
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
        val user = UserProfile.builder()
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
                    response.data?.let { createdUser ->
                        triggerLambdaFunction(createdUser.id, onSuccess, onError)
                    } ?: onError(Exception("User creation succeeded but no data returned"))
                }
            },
            { error -> onError(error) }
        )
    }

    fun updateUser(
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
        checkUserProfile(
            email,
            onProfileFound = { existingUser ->
                val updatedUser = existingUser.copyOfBuilder()
                    .firstname(firstName)
                    .lastname(lastName)
                    .email(email)
                    .consentGiven(true)
                    .age(age)
                    .gender(gender)
                    .skintype(skinType)
                    .productType(productType)
                    .skinProblems(skinProblems)
                    .notableEffects(notableEffects)
                    .build()

                Amplify.API.mutate(
                    ModelMutation.update(updatedUser),
                    { updateResponse ->
                        if (updateResponse.hasErrors()) {
                            onError(Exception(updateResponse.errors.first().message))
                        } else {
                            updateResponse.data?.let { updatedUserProfile ->
                                triggerLambdaFunction(updatedUserProfile.id, onSuccess, onError)
                            } ?: onError(Exception("Update succeeded but no data returned"))
                        }
                    },
                    { error ->
                        onError(error)
                    }
                )
            },
            onProfileNotFound = {
                onError(Exception("Profile not found"))
            },
            onError = { error ->
                onError(error)
            }
        )
    }

    // Trigger the Lambda function by executing the GraphQL mutation
    private fun triggerLambdaFunction(
        userId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val mutation = """
        mutation GenerateRecommendations {
            generateRecommendations(id: "$userId")
        }
        """.trimIndent()

        val request = SimpleGraphQLRequest<String>(
            mutation,
            emptyMap<String, Any>(), // No variables
            String::class.java,  // Expected response type
            null  // No authorization needed
        )

        Amplify.API.mutate(
            request,
            { response ->
                if (response.hasErrors()) {
                    onError(Exception(response.errors.first().message))
                } else {
                    Log.d("UserProfileManager", "Successfully triggered Lambda function for user ID: $userId")
                    onSuccess()
                }
            },
            { error ->
                Log.e("UserProfileManager", "Failed to trigger Lambda function: $error")
                onError(error)
            }
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

    fun checkUserProfile(email: String, onProfileFound: (UserProfile) -> Unit, onProfileNotFound: () -> Unit, onError: (Exception) -> Unit) {
        Amplify.API.query(
            ModelQuery.list(UserProfile::class.java, UserProfile.EMAIL.eq(email)),
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

    fun checkUserProfileID(id: String, onProfileFound: (UserProfile) -> Unit, onProfileNotFound: () -> Unit, onError: (Exception) -> Unit) {
        Amplify.API.query(
            ModelQuery.get(UserProfile::class.java, id),  // Query by `id` instead of `email`
            { response ->
                val user = response.data
                if (user != null) {
                    onProfileFound(user)
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
