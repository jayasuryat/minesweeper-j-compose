package com.jayasuryat.uigame.logic

import androidx.compose.runtime.Immutable

@Immutable
data class GameConfiguration(
    val gameId: String,
    val rows: Int,
    val columns: Int,
    val mines: Int,
)
