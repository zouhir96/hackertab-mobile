import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}


kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.data"

        // Enable Android resources for Compose Multiplatform resources
        androidResources {
            enable = true
        }
    }
    setFrameworkBaseName("data")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(project(":core:network"))
            implementation(project(":core:database"))

            // Datastore
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.androidx.datastore.preferences.core)

            implementation(libs.kotlinx.atomicfu)

            implementation(compose.components.resources)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zrcoding.hackertab.data.resources"
    generateResClass = always
}