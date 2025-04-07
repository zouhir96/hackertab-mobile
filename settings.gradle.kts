pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "hackertab"
include(":app")
include(":core:design")
include(":core:network")
include(":core:di")
include(":feature:settings")
include(":feature:home")
include(":core:domain")
include(":shared")
include(":core:data")
