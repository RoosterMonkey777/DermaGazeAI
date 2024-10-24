package khan.z.dermagazeai.onboard

import androidx.annotation.DrawableRes
import khan.z.dermagazeai.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {

    data object FirstPage : OnboardingModel(
        image = R.drawable.doctor,
        title = "Check your skin",
        description = "Narrow down your skin condition whether you have Eczema or Acne"
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.result,
        title = "What you get?",
        description = "Result in just seconds"
    )

    data object ThirdPages : OnboardingModel(
        image = R.drawable.products,
        title = "Recommendations",
        description = "Get recommendations based on your skin type and diagnoses"
    )

    data object FourthPages : OnboardingModel(
        image = R.drawable.pills,
        title = "Medication",
        description = "Add your current medication and be reminded to take them"
    )


}