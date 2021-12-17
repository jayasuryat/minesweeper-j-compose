package com.jayasuryat.minesweeperengine.gridgenerator

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.grid.Grid

public interface GridGenerator {

    public suspend fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): Grid
}
