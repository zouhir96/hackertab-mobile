import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}
group = "com.zrcoding.hackertab.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatformLibrary"){
            id = "hackertab.kmp.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }
        register("kotlinMultiplatformCompose"){
            id = "hackertab.kmp.compose"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
        register("kotlinMultiplatformFeature"){
            id = "hackertab.kmp.feature"
            implementationClass = "KotlinMultiplatformFeatureConventionPlugin"
        }
    }
}