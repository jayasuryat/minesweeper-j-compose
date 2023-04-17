@file:Suppress("SpellCheckingInspection")

object Dependency {

    object Test {

        const val junit = "junit:junit:4.13.2"
        const val androidJunit = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
    }

    object Module {

        const val util = ":util"

        const val mineSweeperEngine = ":core:minesweeper-engine"
        const val mineSweeperEngineDebug = ":core:minesweeper-engine-debug"
        const val mineSweeperUi = ":core:minesweeper-ui"

        const val gameScreen = ":ui-game"
        const val difficultySelection = ":ui-difficulty-selection"
        const val settings = ":ui-settings"

        const val data = ":data"
    }

    object Compose {

        const val composeCompilerVersion = "1.4.4"
        private const val composeVersion = "1.4.1"

        const val ui = "androidx.compose.ui:ui:$composeVersion"
        const val uiUtil = "androidx.compose.ui:ui-util:$composeVersion"
        const val material = "androidx.compose.material:material:$composeVersion"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
        const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val activity = "androidx.activity:activity-compose:1.7.0"
        const val navigation = "androidx.navigation:navigation-compose:2.5.3"

        private const val accompanaistVersion = "0.30.1"
        const val accompanistInsets =
            "com.google.accompanist:accompanist-insets:$accompanaistVersion"
        const val accompanistPager =
            "com.google.accompanist:accompanist-pager:$accompanaistVersion"
        const val accompanistNavAnimation =
            "com.google.accompanist:accompanist-navigation-animation:$accompanaistVersion"

        object Test {

            const val junit = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        }
    }

    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    const val material = "com.google.android.material:material:1.8.0"

    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"

    private const val taktVersion = "2.1.1"
    const val takt = "jp.wasabeef:takt:$taktVersion"
    const val taktNoOp = "jp.wasabeef:takt-no-op:$taktVersion"

    const val settings = "com.russhwolf:multiplatform-settings-no-arg:1.0.0"
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"

    private const val koinVersion = "3.4.0"
    private const val koinComposeVersion = "3.4.3"
    const val koinCore = "io.insert-koin:koin-core:$koinVersion"
    const val koinAndroid = "io.insert-koin:koin-android:$koinVersion"
    const val koinCompose = "io.insert-koin:koin-androidx-compose:$koinComposeVersion"

    private const val sqldelightVersion = "1.5.5"
    const val sqldelightRuntime = "com.squareup.sqldelight:runtime:$sqldelightVersion"
    const val sqldelightAndroidDriver = "com.squareup.sqldelight:android-driver:$sqldelightVersion"
    const val sqldelightNativeDriver = "com.squareup.sqldelight:native-driver:$sqldelightVersion"
}
