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
include(":core:domain")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:analytics")
include(":feature:onboarding")
include(":feature:home")
include(":feature:settings")
include(":feature:bookmarks")
include(":shared")
