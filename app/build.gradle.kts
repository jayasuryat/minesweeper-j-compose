plugins {
    id("com.android.application")
    kotlin("android")
}

android {

    compileSdk = BuildConfig.compileSdk

    defaultConfig {

        applicationId = "com.jayasuryat.minesweeperjc"

        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

dependencies {

    testImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.Test.androidJunit)
    androidTestImplementation(Dependency.Test.espresso)
    androidTestImplementation(Dependency.Compose.Test.junit)

    debugImplementation(Dependency.Compose.tooling)
    implementation(Dependency.coreKtx)
    implementation(Dependency.appCompat)
    implementation(Dependency.material)
    implementation(Dependency.Compose.ui)
    implementation(Dependency.Compose.material)
    implementation(Dependency.Compose.activity)
    implementation(Dependency.Compose.toolingPreview)
    implementation(Dependency.Compose.accompanistInsets)
    implementation(Dependency.Compose.navigation)

    implementation(Dependency.lifecycleRuntime)

    implementation(project(Dependency.Module.util))
    implementation(project(Dependency.Module.gameScreen))
    implementation(project(Dependency.Module.difficultySelection))

    debugImplementation(Dependency.takt)
    releaseImplementation(Dependency.taktNoOp)
}