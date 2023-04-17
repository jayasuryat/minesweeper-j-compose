import org.gradle.api.JavaVersion

object BuildConfig {

    const val compileSdk: Int = 33
    const val minSdk: Int = 22
    const val targetSdk: Int = 33

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}
