import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}


kotlin {
    setFrameworkBaseName("data")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(project(":core:network"))

            // Datastore
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.datastore.preferences.core)

            implementation(libs.kotlinx.atomicfu)

            implementation(compose.components.resources)
        }
    }
}

android {
    namespace = "com.zrcoding.hackertab.data"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zrcoding.hackertab.data.resources"
    generateResClass = always
}