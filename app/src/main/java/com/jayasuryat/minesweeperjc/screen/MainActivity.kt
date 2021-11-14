package com.jayasuryat.minesweeperjc.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.uigame.GameConfiguration
import com.jayasuryat.uigame.GameScreen
import com.jayasuryat.util.LogCompositions

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MinesweeperJCTheme {

                LogCompositions(name = "MinesweeperJCTheme")

                GameScreen(
                    gameConfiguration = GameConfiguration(
                        rows = 10,
                        columns = 10,
                        mines = 10,
                    ),
                )
            }
        }
    }
}