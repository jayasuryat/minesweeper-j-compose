package com.jayasuryat.minesweeperjc.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jayasuryat.minesweeperjc.features.MineSweeper
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperJCTheme {
                MineSweeper()
            }
        }
    }
}