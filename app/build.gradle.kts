plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.streetwalkermobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.streetwalkermobile"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "dagger.hilt.android.testing.HiltTestRunner"
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "ENVIRONMENT", "\"DEV\"")
            // Local dev API (Android emulator -> host): http://10.0.2.2:8083
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8083\"")
            buildConfigField("String", "MAPS_API_KEY", "\"DEV_MAP_KEY\"")
        }
        create("stage") {
            dimension = "environment"
            applicationIdSuffix = ".stage"
            versionNameSuffix = "-stage"
            buildConfigField("String", "ENVIRONMENT", "\"STAGE\"")
            buildConfigField("String", "API_BASE_URL", "\"https://stage.api.streetwalker.example\"")
            buildConfigField("String", "MAPS_API_KEY", "\"STAGE_MAP_KEY\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "ENVIRONMENT", "\"PROD\"")
            buildConfigField("String", "API_BASE_URL", "\"https://api.streetwalker.example\"")
            buildConfigField("String", "MAPS_API_KEY", "\"PROD_MAP_KEY\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":shared:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:config"))
    implementation(project(":core:logger"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":feature:map"))
    implementation(project(":feature:markers"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:users"))
    implementation(project(":feature:friends"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(libs.androidx.compose.material.icons.extended)

    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt testing for instrumentation (even if we pass VMs manually in tests, keep available)
    androidTestImplementation(libs.hilt.android)
    androidTestImplementation("com.google.dagger:hilt-android-testing:${libs.versions.hilt.get()}")
    kaptAndroidTest(libs.hilt.compiler)
}
