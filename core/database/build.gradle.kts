import com.zrcoding.convention.setFrameworkBaseName

plugins {
    id("hackertab.kmp.library")
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidLibrary {
        namespace = "com.zrcoding.hackertab.database"
    }
    setFrameworkBaseName("database")
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }
    sourceSets {
        commonMain.dependencies {
            // Room
            api(libs.androidx.room.runtime)
            api(libs.androidx.sqlite.bundled)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    listOf(
        "kspAndroid",
        // "kspJvm",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.androidx.room.compiler)
    }
}