plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
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
    }
}

tasks.register("ciCheck") {
    dependsOn(
        "detekt",
        ":app:lint",
        ":app:testDevDebugUnitTest"
    )
}
