package com.jayasuryat.minesweeperengine.util

import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperengine.model.grid.MutableMineGrid


internal fun MineGrid.toMutable(): MutableMineGrid {
    return MutableMineGrid.from(cells = cells)
}

internal fun MutableMineGrid.toImmutable(): MineGrid {
    return MineGrid(
        gridSize = gridSize,
        cells = cells.map { it.toList() }.toList(),
    )
}

internal fun Grid.mutate(
    mutation: MutableMineGrid.() -> Unit,
): Grid {
    val modGrid = MutableMineGrid.from(this)
    return modGrid.apply(mutation).toImmutable()
}