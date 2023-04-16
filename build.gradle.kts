buildscript {
//    ext {
//        kotlin_version = "1.7.10"
//        retrofit_version = "2.9.0"
//        hilt_navigation_compose_version = "1.0.0"
//        lifecycle_viewmodel_compose_version = "2.5.1"
//        hilt_version = "2.43.1"
//        glide_version = "1.6.1"
//        firebase_version = "30.2.0"
//        junit_version = "4.13.2"
//        activity_compose_version = "1.5.1"
//        lifecycle_runtime_ktx_version = "2.5.1"
//        core_ktx_version = "1.8.0"
//        compose_version = "1.4.0-rc01"
//        compose_compiler_version = "1.3.1"
//        nav_version = "2.5.1"
//    }
    repositories {
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}