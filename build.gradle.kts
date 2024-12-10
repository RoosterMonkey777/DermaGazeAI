// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2") // Your existing Gradle version
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.1") // Safe Args classpath

    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
