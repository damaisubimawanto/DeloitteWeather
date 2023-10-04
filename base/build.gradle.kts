plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.damai.base"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    dataBinding {
        enable = true
    }
}

dependencies {

    api("androidx.appcompat:appcompat:1.6.1")
    api("androidx.constraintlayout:constraintlayout:2.1.4")
    api("androidx.core:core-ktx:1.10.1")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    api("androidx.navigation:navigation-fragment-ktx:2.3.5")
    api("androidx.room:room-ktx:2.5.2")
    api("androidx.room:room-runtime:2.5.2")
    api("com.google.android.material:material:1.9.0")

    api("com.github.bumptech.glide:glide:4.15.1")

    api("io.insert-koin:koin-android:3.4.2")
    api("io.insert-koin:koin-core:3.4.2")

    api("com.squareup.okhttp3:logging-interceptor:4.9.0")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")

    ksp("com.github.bumptech.glide:ksp:4.15.1")

    androidTestApi("androidx.test.espresso:espresso-core:3.5.1")
    androidTestApi("androidx.test.ext:junit:1.1.5")

    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.test.ext:junit:1.1.5")
}