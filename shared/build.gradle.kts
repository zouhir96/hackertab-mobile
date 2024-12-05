import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
    alias(libs.plugins.jetbrains.kotlinx.serialization)
}

kotlin {
    setFrameworkBaseName("shared")

    sourceSets.commonMain.dependencies {
        implementation(project(":core:design"))
        implementation(project(":core:network"))

        implementation(project(":feature:home"))
        implementation(project(":feature:settings"))

        implementation(libs.kotlinx.serialization.json)
        implementation(libs.androidx.navigation)
    }
}

android {
    namespace = "com.zrcoding.hackertab.shared"
}
