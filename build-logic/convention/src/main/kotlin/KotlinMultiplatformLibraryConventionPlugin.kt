import com.android.build.api.dsl.LibraryExtension
import com.zrcoding.convention.configureKotlinAndroid
import com.zrcoding.convention.configureKotlinMultiplatform
import com.zrcoding.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(versionCatalog().findPlugin("kotlin-multiplatform").get().get().pluginId)
                apply(versionCatalog().findPlugin("android-library").get().get().pluginId)
            }
            extensions.configure(
                KotlinMultiplatformExtension::class.java,
                ::configureKotlinMultiplatform
            )
            extensions.configure(
                LibraryExtension::class.java,
                ::configureKotlinAndroid
            )
        }
    }
}