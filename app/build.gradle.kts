plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ua.currency.exchanger.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ua.currency.exchanger.app"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(projects.coroutine)

    implementation(projects.architectureUi)
    implementation(projects.architecturePresentation)
    implementation(projects.architectureDomain)

    implementation(projects.exchangeData)
    implementation(projects.exchangeDomain)
    implementation(projects.exchangePresentation)
    implementation(projects.exchangeUi)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    debugImplementation(libs.debug.compose.ui.tooling)
    debugImplementation(libs.debug.compose.ui.manifest)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.konsist)

}

kapt {
    correctErrorTypes = true
}


