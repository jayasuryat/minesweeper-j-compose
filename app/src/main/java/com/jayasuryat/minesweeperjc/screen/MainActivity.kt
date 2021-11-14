package com.jayasuryat.minesweeperjc.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.uigame.GameScreen
import com.jayasuryat.uigame.GameConfiguration

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