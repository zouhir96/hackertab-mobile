import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

android {
    namespace = "com.zrcoding.hackertab.analytics"
}

kotlin {
    setFrameworkBaseName("analytics")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.napier)
        }
        androidMain.dependencies {
            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }
        iosMain.dependencies {
            // Firebase
            implementation(libs.gitlive.analytics)
        }
    }
}