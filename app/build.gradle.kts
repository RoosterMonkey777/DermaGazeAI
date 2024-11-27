plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "khan.z.dermagazeai"
    compileSdk = 34

    defaultConfig {
        applicationId = "khan.z.dermagazeai"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Matching your Kotlin version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation("com.tbuonomo:dotsindicator:5.0")

    // Jetpack Compose Dependencies
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.material3:material3:1.2.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.1")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.1")

    // ConstraintLayout for Compose
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation("androidx.navigation:navigation-compose:2.7.1") // Compose navigation

    // Amplify
    implementation(libs.amplify.api)
    implementation(libs.amplify.datastore)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.amplify.auth.cognito)
    implementation(libs.amplify.core)
    implementation(libs.aws.storage.s3)

    // Facebook
    implementation(libs.facebook.login)

    // Google
    implementation(libs.play.services.auth)

    // Glide
    implementation(libs.glide)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
