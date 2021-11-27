package com.jayasuryat.minesweeperengine.util

import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperengine.model.grid.MutableMineGrid

internal fun MutableMineGrid.toImmutable(): MineGrid {
    return MineGrid(
        gridSize = gridSize,
        totalMines = totalMines,
        cells = cells.map { it.toList() }.toList(),
    )
}

internal fun Grid.mutate(
    mutation: MutableMineGrid.() -> Unit,
): Grid {
    val modGrid = MutableMineGrid.from(this)
    return modGrid.apply(mutation).toImmutable()
}