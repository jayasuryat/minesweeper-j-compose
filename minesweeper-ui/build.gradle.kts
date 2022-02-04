plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
}

android {

    compileSdk = BuildConfig.compileSdk

    defaultConfig {

        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

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
        sourceCompatibility = BuildConfig.javaVersion
        targetCompatibility = BuildConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependency.Compose.composeVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs += "-Xexplicit-api=strict"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

dependencies {

    testImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.Test.androidJunit)
    androidTestImplementation(Dependency.Test.espresso)
    androidTestImplementation(Dependency.Compose.Test.junit)

    implementation(Dependency.coreKtx)
    implementation(Dependency.material)

    implementation(Dependency.Compose.ui)
    implementation(Dependency.Compose.material)
    implementation(Dependency.Compose.toolingPreview)
    debugImplementation(Dependency.Compose.tooling)

    implementation(project(Dependency.Module.util))
    implementation(project(Dependency.Module.mineSweeperEngine))
}