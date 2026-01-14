import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.analytics"
    }
    setFrameworkBaseName("analytics")

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
        }

        commonMain.dependencies {
            implementation(project(":core:domain"))

            implementation(libs.napier)
            implementation(libs.gitlive.analytics)
        }
    }
}