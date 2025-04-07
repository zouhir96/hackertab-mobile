import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

kotlin {
    setFrameworkBaseName("settings")
    sourceSets.commonMain.dependencies {
        // Datastore
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.preferences.core)

        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.atomicfu)
    }
}

android {
    namespace = "com.zrcoding.hackertab.settings"
}