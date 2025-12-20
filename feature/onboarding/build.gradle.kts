import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.onboarding"
    }
    setFrameworkBaseName("onboarding")
}