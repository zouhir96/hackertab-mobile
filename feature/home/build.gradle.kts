import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

android {
    namespace = "com.zrcoding.hackertab.home"
}

kotlin {
    setFrameworkBaseName("home")
    sourceSets.commonMain.dependencies {
        implementation(project(":feature:settings"))
    }
}