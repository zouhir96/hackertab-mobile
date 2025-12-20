import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.design"

        // Enable Android resources for Compose Multiplatform resources
        androidResources {
            enable = true
        }
    }
    setFrameworkBaseName("design")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(libs.kevinnzou.webview)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zrcoding.hackertab.design.resources"
    generateResClass = always
}