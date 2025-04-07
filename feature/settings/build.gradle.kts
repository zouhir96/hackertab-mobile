import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

kotlin {
    setFrameworkBaseName("settings")
}

android {
    namespace = "com.zrcoding.hackertab.settings"
}