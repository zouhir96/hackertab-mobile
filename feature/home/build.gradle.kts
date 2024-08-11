
plugins {
    id("hackertab.kmp.feature")
}

android {
    namespace = "com.zrcoding.hackertab.home"
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":feature:settings"))
    }
}