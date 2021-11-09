package com.jayasuryat.minesweeperengine.util

import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperengine.model.grid.MutableMineGrid


internal fun MineGrid.toMutable(): MutableMineGrid {
    return MutableMineGrid.from(cells = cells)
}

internal fun MutableMineGrid.toImmutableMutable(): MineGrid {
    return MineGrid(
        gridSize = gridSize,
        cells = cells.map { it.toList() }.toList(),
    )
}
