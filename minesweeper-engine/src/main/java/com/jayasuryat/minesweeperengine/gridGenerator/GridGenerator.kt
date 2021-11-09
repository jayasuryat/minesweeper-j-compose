package com.jayasuryat.minesweeperengine.gridGenerator

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.grid.MineGrid

internal interface GridGenerator {

    fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): MineGrid
}