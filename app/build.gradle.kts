plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("dagger.hilt.android.plugin")

    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.astrog.sheduleapp"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    val retrofitVersion = "2.9.0"
    val hiltNavigationComposeVersion = "1.0.0"
    val hiltVersion = "2.45"
    val navVersion = "2.5.1"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.4.0-rc01")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val datePickerVersion = "1.1.1"
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:$datePickerVersion")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:$datePickerVersion")
}