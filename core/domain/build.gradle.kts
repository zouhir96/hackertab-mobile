import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
}

kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.domain"
    }
    setFrameworkBaseName("domain")
}