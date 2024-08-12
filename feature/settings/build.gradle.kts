import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
    alias(libs.plugins.jetbrains.kotlinx.serialization)
}

kotlin {
    setFrameworkBaseName("settings")
    sourceSets.commonMain.dependencies {
        // Datastore
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.preferences.core)

        implementation(libs.kotlinx.serialization.json)

        implementation("org.jetbrains.kotlinx:atomicfu:0.24.0")
    }
}

android {
    namespace = "com.zrcoding.hackertab.settings"
}