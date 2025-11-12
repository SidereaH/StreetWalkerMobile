plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
    id("jacoco")
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(files("config/detekt/detekt.yml"))
    autoCorrect = true
}

subprojects {
    afterEvaluate {
        plugins.withId("io.gitlab.arturbosch.detekt") {
            extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
                config.setFrom(files(rootProject.file("config/detekt/detekt.yml")))
            }
        }
        // Apply JaCoCo to all Android modules as per docs
        plugins.withId("com.android.application") { pluginManager.apply("jacoco") }
        plugins.withId("com.android.library") { pluginManager.apply("jacoco") }
    }
}

tasks.register("ciCheck") {
    dependsOn(
        "detekt",
        ":app:lint",
        ":app:testDevDebugUnitTest"
    )
}

// --- JaCoCo multi-module coverage aggregation (unit + androidTest) ---
jacoco {
    toolVersion = "0.8.10"
}

// Merge all execution data from subprojects (both .exec and .ec)
tasks.register<JacocoMerge>("jacocoMergeAll") {
    group = "verification"
    description = "Merges all JaCoCo execution data across modules"
    executionData(fileTree(projectDir) {
        include("**/build/**/*.exec")
        include("**/build/**/*.ec")
    })
    destinationFile = layout.buildDirectory.file("jacoco/merged.exec").get().asFile
    doFirst {
        // Filter missing files to avoid warnings
        executionData = executionData.filter { it.exists() }
    }
}

tasks.register<JacocoReport>("jacocoReportAll") {
    group = "verification"
    description = "Generates a combined coverage report for all modules"
    dependsOn("jacocoMergeAll")

    val classDirs = files(subprojects.map { sp ->
        sp.fileTree("${sp.buildDir}/tmp/kotlin-classes/debug") {
            exclude(
                // Exclude generated and Android classes
                "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
                "**/*Test*.*", "**/androidTest/**", "**/*Hilt*.*", "**/*_Factory.*",
                "**/*Directions*.*", "**/*Args*.*"
            )
        }
    })

    classDirectories.setFrom(classDirs)
    sourceDirectories.setFrom(files(subprojects.map { sp -> listOf(sp.file("src/main/java"), sp.file("src/main/kotlin")) }))
    executionData.setFrom(layout.buildDirectory.file("jacoco/merged.exec"))

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/jacocoReportAll.xml"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
    }
}
