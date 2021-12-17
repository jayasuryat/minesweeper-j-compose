import org.gradle.api.JavaVersion

object BuildConfig {

    const val compileSdk: Int = 31
    const val minSdk: Int = 22
    const val targetSdk: Int = 31

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}
