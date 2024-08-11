
import com.zrcoding.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", versionCatalog().findLibrary("koin.core").get())
                add("implementation", versionCatalog().findLibrary("koin.android").get())
            }
        }
    }
}