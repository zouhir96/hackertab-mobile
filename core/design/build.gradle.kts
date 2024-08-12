import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    id("hackertab.kmp.compose")
}

kotlin {
    setFrameworkBaseName("design")
}

android {
    namespace = "com.zrcoding.hackertab.design"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.zrcoding.hackertab.design.resources"
    generateResClass = always
}