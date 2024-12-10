package khan.z.dermagazeai.manager

import com.amplifyframework.core.model.annotations.ModelConfig
import com.amplifyframework.core.model.annotations.ModelField
import com.amplifyframework.core.model.Model
import java.util.UUID

@ModelConfig(pluralName = "SkinCareProducts")
data class SkinCareProduct(
    @ModelField(targetType = "ID", isRequired = true)
    val id: String = UUID.randomUUID().toString(), // Automatically generate a UUID

    @ModelField(targetType = "String", isRequired = true)
    val productName: String,

    @ModelField(targetType = "String", isRequired = true)
    val productType: String,

    @ModelField(targetType = "String", isRequired = false)
    val brand: String?,

    @ModelField(targetType = "String", isRequired = false)
    val description: String?,

    @ModelField(targetType = "String", isRequired = false)
    val labels: String?,

    @ModelField(targetType = "String", isRequired = false)
    val price: String?,

    @ModelField(targetType = "String", isRequired = false)
    val productHref: String?,

    @ModelField(targetType = "String", isRequired = false)
    val pictureSrc: String?,

    @ModelField(targetType = "AWSList", isRequired = false)
    val notableEffects: List<String>?,

    @ModelField(targetType = "AWSList", isRequired = false)
    val skintype: List<String>?,

    @ModelField(targetType = "AWSList", isRequired = false)
    val skinTypes: List<String>?
) : Model
