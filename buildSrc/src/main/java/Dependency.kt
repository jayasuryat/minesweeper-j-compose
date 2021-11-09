@file:Suppress("SpellCheckingInspection")

object Dependency {

    object Test {

        const val junit = "junit:junit:4.13.2"
        const val androidJunit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Module {

        const val mineSweeperEngine = ":minesweeper-engine"
    }

    object Compose {

        const val composeVersion = "1.0.5"

        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val material = "androidx.compose.material:material:$composeVersion"
        const val tooling = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val activity = "androidx.activity:activity-compose:1.4.0"

        object Test {

            const val junit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        }
    }

    const val coreKtx = "androidx.core:core-ktx:1.7.0"
    const val appCompat = "androidx.appcompat:appcompat:1.3.1"
    const val material = "com.google.android.material:material:1.4.0"

    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"

}