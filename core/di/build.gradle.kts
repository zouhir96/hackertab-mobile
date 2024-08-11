plugins {
    id("hackertab.kmp.library")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.moko.mvvm.core)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "com.zrcoding.hackertab.di"
}
