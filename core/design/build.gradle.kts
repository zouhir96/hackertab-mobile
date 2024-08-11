plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

android {
    namespace = "com.zrcoding.hackertab.design"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zrcoding.hackertab.design.resources"
    generateResClass = always
}