buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    alias(stack.plugins.kotlin.android) apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}