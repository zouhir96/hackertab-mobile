import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
}

kotlin {
    setFrameworkBaseName("domain")
}

android {
    namespace = "com.zrcoding.hackertab.network"
}