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
    val cells: List<CellInfo>,
) {

    @Stable
    public data class CellInfo(
        val cellState: State<RawCell>,
        val position: Position,
    )

    public companion object {

        @Stable
        public fun from(statefulGrid: StatefulGrid): GridLayoutInformation {

            return GridLayoutInformation(
                gridSize = statefulGrid.gridSize,
                cells = statefulGrid.cells.flatten().map { cell ->
                    CellInfo(cellState = cell, position = cell.value.position)
                }
            )
        }
    }
}