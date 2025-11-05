plugins {
    kotlin("android") version "1.9.10" apply false
    kotlin("jvm") version "1.9.10" apply false
    id("com.android.application") version "8.4.0" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false  // <-- KSP plugin
    id("com.google.dagger.hilt.android") version "2.57.2" apply false   // Hilt plugin
}


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Hilt Gradle plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
    }
}