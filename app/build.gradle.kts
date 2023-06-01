plugins {
    id("com.android.application")
    kotlin("android")

    kotlin("kapt")
    id("dagger.hilt.android.plugin")

    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.astrog.sheduleapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 2
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
        kotlinCompilerExtensionVersion = androidx.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.astrog.sheduleapp"
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2023.04.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    val hiltNavigationComposeVersion = "1.0.0"
    val hiltVersion = "2.45"
    val navVersion = "2.5.1"

    implementation(stack.kotlin.logging)

    implementation(stack.okhttp.loggingInterceptor)

    implementation("androidx.core:core-ktx")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.activity:activity-compose")

    implementation(stack.accompanist.systemuicontroller)

    implementation(androidx.room)
    implementation(androidx.room.runtime)
    kapt(androidx.room.compiler)

    testImplementation(stack.junit4)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")

    implementation(stack.retrofit)
    implementation(stack.retrofit.converter.gson)

    val datePickerVersion = "1.1.1"
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:$datePickerVersion")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:$datePickerVersion")
}