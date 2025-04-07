package com.zrcoding.convention

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    jvmToolchain(JavaLanguageVersion.of(17).asInt())

    // targets
    androidTarget()

    applyDefaultHierarchyTemplate()

    //common dependencies
    sourceSets.apply {
        commonMain {
            dependencies {
                implementation(versionCatalog().findLibrary("koin.core").get())
                implementation(versionCatalog().findLibrary("kotlinx.datetime").get())
                implementation(versionCatalog().findLibrary("kotlinx.coroutines").get())
                implementation(versionCatalog().findLibrary("kotlinx.serialization.json").get())
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