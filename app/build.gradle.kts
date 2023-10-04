plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.damai.deloitteweather"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.damai.deloitteweather"
        minSdk = 24
        targetSdk = 33
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation(project(":base"))
    implementation(project(":data"))
    implementation(project(":domain"))

    ksp("androidx.room:room-compiler:2.5.2")

    // Minimal test
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.test.ext:junit:1.1.5")

    // Advanced test
    testImplementation("androidx.test:runner:1.2.0")
    testImplementation("com.jraska.livedata:testing-ktx:1.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("io.mockk:mockk:1.13.5")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.3")

    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.2.0")
}