package com.zrcoding.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    jvmToolchain(versionCatalog().findVersion("jvmTarget").get().toString().toInt())

    // targets
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    //common dependencies
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(versionCatalog().findLibrary("koin.core").get())
                implementation(versionCatalog().findLibrary("kotlinx.datetime").get())
            }
        }

        androidMain {
            dependencies {
                implementation(versionCatalog().findLibrary("koin.android").get())
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}