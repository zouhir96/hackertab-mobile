import com.zrcoding.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("hackertab.kmp.library")
                apply("hackertab.kmp.compose")
            }
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(project(":core:design"))
                    implementation(project(":core:domain"))
                    implementation(project(":core:network"))
                    implementation(project(":core:di"))

                    implementation(versionCatalog().findLibrary("koin.compose").get())
                }
            }
        }
    }
}