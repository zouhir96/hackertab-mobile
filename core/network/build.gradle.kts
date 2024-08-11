plugins {
    id("hackertab.kmp.library")
    alias(libs.plugins.jetbrains.kotlinx.serialization)
}

android {
    namespace = "com.zrcoding.hackertab.network"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Ktor
            implementation(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.loggingInterceptor)
        }
        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
    }
}