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

            implementation(project(":feature:onboarding"))
            implementation(project(":feature:home"))
            implementation(project(":feature:settings"))
        }
    }
}

android {
    namespace = "com.zrcoding.hackertab.shared"
}
