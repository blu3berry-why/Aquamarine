// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        google()
        mavenCentral()
    }

    dependencies{
        classpath(Dependencies.hiltAgp)
    }

}



plugins {
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.android.library") version "8.4.1" apply false
    kotlin("plugin.serialization") version "1.9.24" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1" apply false
}
