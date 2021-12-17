package com.jayasuryat.minesweeperjc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jayasuryat.minesweeperjc.theme.MinesweeperJCTheme

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
