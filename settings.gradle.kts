pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("androidx") {
            from(files("gradle/android.libs.versions.toml"))
        }
        create("stack") {
            from(files("gradle/stack.libs.versions.toml"))
        }
    }
}

rootProject.name = "SheduleApp"
include(":app")
