package com.jayasuryat.minesweeperjc.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.insets.ProvideWindowInsets
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.util.LogCompositions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MinesweeperJCTheme {

                LogCompositions(name = "MinesweeperJCTheme")

                ProvideWindowInsets {

                    MinesweeperGame()
                }
            }
        }
    }
}