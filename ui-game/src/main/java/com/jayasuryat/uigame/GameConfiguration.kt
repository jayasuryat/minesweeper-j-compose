package com.jayasuryat.uigame

import androidx.compose.runtime.Immutable

@Immutable
data class GameConfiguration(
    val rows: Int,
    val columns: Int,
    val mines: Int,
)