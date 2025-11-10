pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "StreetWalkerMobile"
include(
    ":app",
    ":core:common",
    ":core:config",
    ":core:database",
    ":core:logger",
    ":core:network",
    ":feature:friends",
    ":feature:map",
    ":feature:markers",
    ":feature:profile",
    ":feature:users",
    ":shared:ui"
)
