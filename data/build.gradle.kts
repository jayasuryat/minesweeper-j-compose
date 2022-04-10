plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlinx-serialization")
}

version = "1.0"

kotlin {

    android()

    val sdkName = System.getenv("SDK_NAME")
    if (sdkName != null && sdkName.startsWith("iphoneos")) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "data"
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(Dependency.settings)
                implementation(Dependency.kotlinxSerialization)
                implementation(Dependency.sqldelightRuntime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Dependency.sqldelightAndroidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(Dependency.sqldelightNativeDriver)
            }
        }
        val iosTest by getting {
            dependsOn(commonTest)
        }
    }

    explicitApi()
}

android {
    compileSdk = BuildConfig.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
    }
}

sqldelight {
    database("MinesweeperDatabase") {
        packageName = "com.jayasuryat.data"
    }
}