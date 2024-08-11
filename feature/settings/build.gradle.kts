plugins {
    id("hackertab.kmp.feature")
    alias(libs.plugins.jetbrains.kotlinx.serialization)
}

kotlin {
    sourceSets.commonMain.dependencies {
        // Datastore
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.preferences.core)

        implementation(libs.kotlinx.serialization.json)
    }
}

android {
    namespace = "com.zrcoding.hackertab.settings"
}