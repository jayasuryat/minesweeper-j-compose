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

dependencies {

    testImplementation(Dependency.Test.junit)
    androidTestImplementation(Dependency.Test.androidJunit)
    androidTestImplementation(Dependency.Test.espresso)
    androidTestImplementation(Dependency.Compose.Test.junit)

    implementation(Dependency.coreKtx)
    implementation(Dependency.appCompat)
    implementation(Dependency.material)
    implementation(Dependency.Compose.ui)
    implementation(Dependency.Compose.material)
    implementation(Dependency.Compose.activity)
    implementation(Dependency.Compose.toolingPreview)
    debugImplementation(Dependency.Compose.tooling)

    implementation(Dependency.lifecycleRuntime)

    implementation(project(Dependency.Module.mineSweeperEngine))
    implementation(project(Dependency.Module.mineSweeperEngineDebug))

    implementation(project(Dependency.Module.mineSweeperUi))
    implementation(project(Dependency.Module.gameScreen))

    releaseImplementation("jp.wasabeef:takt-no-op:2.1.1")
    debugImplementation("jp.wasabeef:takt:2.1.1")
}