// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.42.0")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath(kotlin("serialization", version = "1.6.20"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply {
    from("buildScripts/versionsPlugin.gradle")
    from("buildScripts/spotless.gradle")
}

plugins {
    id("com.diffplug.spotless") version ("6.5.1")
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
            targetExclude(
                "$buildDir/**/*.kt",
                "bin/**/*.kt",
                "buildSrc/**/*.kt",
                "util/src/main/java/com/jayasuryat/util/LogCompositions.kt",
            )
            ktlint("0.41.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
            licenseHeaderFile(rootProject.file("buildScripts/copyright.txt"))
        }
    }
}

subprojects {

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {

        kotlinOptions {

            if (project.findProperty("myapp.enableComposeCompilerReports") == "true") {
                kotlinOptions.freeCompilerArgs += "-P"
                kotlinOptions.freeCompilerArgs += "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + rootProject.buildDir + "/compose_metrics/"
                kotlinOptions.freeCompilerArgs += "-P"
                kotlinOptions.freeCompilerArgs += "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + rootProject.buildDir + "/compose_metrics/"
            }
        }
    }
}