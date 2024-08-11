import com.zrcoding.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        val composeDeps = extensions.getByType(ComposeExtension::class.java).dependencies

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain.dependencies {
                implementation(composeDeps.runtime)
                implementation(composeDeps.ui)
                implementation(composeDeps.foundation)
                implementation(composeDeps.materialIconsExtended)
                implementation(composeDeps.material)

                implementation(versionCatalog().findLibrary("io.coil.compose").get())
                implementation(versionCatalog().findLibrary("io.coil.gif").get())
            }
        }
    }
}