import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
}

kotlin {

    setFrameworkBaseName("di")

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
