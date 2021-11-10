package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import  androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.util.dp
import com.jayasuryat.minesweeperui.composable.util.floatValue

@Composable
internal fun MineGrid(
    modifier: Modifier,
    mineGrid: MineGrid,
) {

    LogCompositions(name = "inside MineGrid")

    BoxWithConstraints(
        modifier = modifier,
    ) {

        LogCompositions(name = "inside MineGrid BoxWithConstraints")

        val width = maxWidth

        val cellSize = getCellSize(
            gridSize = mineGrid.gridSize,
            width = width,
        )
        val overlap = 1f
        val overlappingCellSize = cellSize + overlap

        mineGrid.cells.flatten().forEach { cell ->

            val offset = cell.position.asOffset(cellSize = cellSize)

            RawCell(
                modifier = Modifier
                    .size(overlappingCellSize.dp())
                    .graphicsLayer {
                        translationX = offset.x
                        translationY = offset.y
                    },
                cell = cell,
            )
        }
    }
}

@Composable
@Stable
private fun getCellSize(
    gridSize: GridSize,
    width: Dp,
): Float {
    val defCellWidth = width / gridSize.columns
    return defCellWidth.floatValue()
}

@Stable
private fun Position.asOffset(
    cellSize: Float,
): Offset {

    val xOff = cellSize * this.column
    val yOff = cellSize * this.row
    return Offset(x = xOff, y = yOff)
}