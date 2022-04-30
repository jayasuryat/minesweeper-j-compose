package com.jayasuryat.uigame.logic.model

import com.jayasuryat.minesweeperengine.model.grid.Grid

sealed interface InitialGrid {

    val grid: Grid

    data class InProgressGrid(
        val startTime: Long,
        override val grid: Grid,
    ) : InitialGrid

    data class NewGrid(
        override val grid: Grid,
    ) : InitialGrid
}