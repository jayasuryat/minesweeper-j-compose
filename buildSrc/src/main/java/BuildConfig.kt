import org.gradle.api.JavaVersion

object BuildConfig {

    const val compileSdk: Int = 34
    const val minSdk: Int = 22
    const val targetSdk: Int = 34

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}
