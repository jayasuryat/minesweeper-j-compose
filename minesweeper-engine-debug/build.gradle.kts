import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = BuildConfig.javaVersion
    targetCompatibility = BuildConfig.javaVersion
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
}

dependencies {

    implementation(project(Dependency.Module.mineSweeperEngine))
}