package khan.z.dermagazeai.manager

import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User

class UserProfileManager {

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

    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        age: Int?,
        gender: String,
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
}
