package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.state.StatefulGrid

@Stable
public data class GridLayoutInformation(
    val gridSize: GridSize,
    val cellData: List<Pair<State<RawCell>, Position>>,
) {

    public companion object {

        public fun from(grid: StatefulGrid): GridLayoutInformation {

            return GridLayoutInformation(
                gridSize = grid.gridSize,
                cellData = grid.cells.flatten().map { cell ->
                    Pair(cell, cell.value.position)
                }
            )
        }
    }
}