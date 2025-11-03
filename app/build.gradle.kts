plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "streetwalker.mobile"
    compileSdk = 36

    defaultConfig {
        applicationId = "streetwalker.mobile"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.57.2")
    kapt("com.google.dagger:hilt-compiler:2.57.2")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.57.2")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.57.2")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.57.2")
    kaptTest("com.google.dagger:hilt-compiler:2.57.2")
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")

    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit + Moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    /// MapLibre core SDK
    implementation("org.maplibre.gl:android-sdk:12.0.1")
// текущая стабильная версия на Maven Central. :contentReference[oaicite:1]{index=1}
// Annotation plugin (для добавления/управления маркерами через SymbolManager)
    implementation("org.maplibre.gl:android-plugin-annotation-v9:3.0.2")
// плагин аннотаций. :contentReference[oaicite:2]{index=2}
// (опционально) MapLibre Compose wrapper (если хочешь Compose-native обёртку)
    implementation("org.maplibre.compose:maplibre-compose:0.11.1")

    //p;ugins
    implementation("org.maplibre.gl:android-plugin-annotation-v9:3.0.2")
    implementation("org.maplibre.gl:android-plugin-markerview-v9:3.0.2")
    implementation("org.maplibre.gl:android-plugin-offline-v9:3.0.2")
    implementation("org.maplibre.gl:android-plugin-building-v9:3.0.2")
// или см. репозиторий maplibre-compose. :contentReference[oaicite:3]{index=3}
//     implementation("org.maplibre.gl:android-sdk:10.4.2") // MapLibre
    // implementation("com.google.android.gms:play-services-maps:18.1.0") // Google Maps

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")



}