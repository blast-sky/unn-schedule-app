buildscript {
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