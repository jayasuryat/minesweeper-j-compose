// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.39.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply {
    from("buildScripts/versionsPlugin.gradle")
    from("buildScripts/spotless.gradle")
}

plugins {
    id("com.diffplug.spotless") version ("5.14.0")
}

allprojects {

    apply {
        plugin("com.diffplug.spotless")
    }

    spotless {

        format("misc") {
            target("**/*.gradle", "**/*.md", "**/.gitignore")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }

        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint("0.41.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}