import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

kotlin {
    setFrameworkBaseName("shared")

    sourceSets.commonMain.dependencies {
        implementation(project(":core:design"))
        implementation(project(":core:network"))

        implementation(project(":feature:home"))
        implementation(project(":feature:settings"))

        implementation(libs.voyager)
    }
}

android {
    namespace = "com.zrcoding.hackertab.shared"
}
