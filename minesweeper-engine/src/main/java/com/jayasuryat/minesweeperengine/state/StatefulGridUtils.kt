package com.jayasuryat.minesweeperengine.state

import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperengine.state.impl.MutableStatefulGrid

public fun Grid.asStatefulGrid(): StatefulGrid {
    return MutableStatefulGrid.from(this)
}

public fun StatefulGrid.getCurrentGrid(): Grid {

    return MineGrid(
        gridSize = this.gridSize,
        cells = this.cells.map { row ->
            row.map { cell ->
                cell.value
            }
        }
    )
}