// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:9.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
        classpath("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.2.10")
    }
}



tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

plugins {
    // ... (plugin lain)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
}
