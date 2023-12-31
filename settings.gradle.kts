pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CurrencyExchanger"
include(":app")
include(":coroutine")
include(":architecture-domain")
include(":architecture-presentation")
include(":architecture-ui")
include(":architecture-presentation-test")
include(":exchange-domain")
include(":exchange-presentation")
include(":exchange-ui")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":coroutine-test")
include(":exchange-data")
