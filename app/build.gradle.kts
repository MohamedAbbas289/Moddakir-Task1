plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.plugin.parcelize")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.moddakirtask1"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.moddakirtask1"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.googleid)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    // Logging interceptor
    implementation(libs.okhttp.logging)
    // Glide
    implementation(libs.glide)
    // View model
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.lifecycle.viewmodel)
    // Dependency injection Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //lottie
    implementation(libs.lottie)
    //sliderView
    implementation(libs.auto.image.slider)
    //implement data layer
    implementation(project(":data"))
    //implement domain layer
    implementation(project(":domain"))
}