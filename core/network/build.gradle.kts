import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
}

kotlin {
    setFrameworkBaseName("network")

    sourceSets {
        commonMain.dependencies {
            // Ktor
            api(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.loggingInterceptor)
        }
        androidMain.dependencies {
            implementation(libs.ktor.cio)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
    }
}

android {
    namespace = "com.zrcoding.hackertab.network"
}