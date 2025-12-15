import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.feature")
}

android {
    namespace = "com.zrcoding.hackertab.bookmarks"
}

kotlin {
    setFrameworkBaseName("bookmarks")
}

