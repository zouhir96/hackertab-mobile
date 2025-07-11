import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "com.zrcoding.hackertab"

    compileSdk = Integer.parseInt(libs.versions.compileSdk.get())
    defaultConfig {
        applicationId = "com.zrcoding.hackertab"
        // The CI will take care of incrementing this using the build number.
        versionCode = 3
        versionName = libs.versions.versionName.get()


        minSdk = Integer.parseInt(libs.versions.minSdk.get())
        targetSdk = Integer.parseInt(libs.versions.targetSdk.get())
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":shared"))

    implementation(project(":core:domain"))
    implementation(project(":feature:home"))
    implementation(project(":feature:settings"))

    // Activity
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity)

    implementation(libs.androidx.compose.materialWindow)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    implementation(libs.napier)
}

tasks.withType<Test> {
    testLogging {
        // set options for log level LIFECYCLE
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT
        )
    }
}