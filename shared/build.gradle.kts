import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

kotlin {
    setFrameworkBaseName("shared")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:design"))
            implementation(project(":core:domain"))
            implementation(project(":core:network"))
            implementation(project(":core:data"))
            implementation(project(":core:analytics"))

            implementation(project(":feature:onboarding"))
            implementation(project(":feature:home"))
            implementation(project(":feature:settings"))
            implementation(project(":feature:bookmarks"))

            implementation(libs.gitlive.crashlytics)
            implementation(libs.gitlive.analytics)
        }
    }
}

android {
    namespace = "com.zrcoding.hackertab.shared"
}
