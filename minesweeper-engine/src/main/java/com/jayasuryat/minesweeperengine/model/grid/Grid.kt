package com.jayasuryat.minesweeperengine.model.grid

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell

internal interface Grid {

    val gridSize: GridSize

    val cells: List<List<RawCell>>

    operator fun get(position: Position): RawCell
}