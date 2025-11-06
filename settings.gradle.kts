pluginManagement {
    val kotlinVersion = "2.0.20"
    val kspVersion = "1.9.24-1.0.20"
    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.id == "com.google.devtools.ksp" ->
                    useModule("com.google.devtools.ksp:symbol-processing-gradle-plugin:$kspVersion")
                requested.id.namespace == "org.jetbrains.kotlin" ->
                    useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            }
        }
    }
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/ksp/dev")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/ksp/dev")
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
    ":shared:ui"
)
