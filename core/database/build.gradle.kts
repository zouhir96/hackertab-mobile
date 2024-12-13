plugins {
    id("hackertab.android.library")
    id("hackertab.android.koin")
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.zrcoding.hackertab.database"
}

dependencies {

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
}