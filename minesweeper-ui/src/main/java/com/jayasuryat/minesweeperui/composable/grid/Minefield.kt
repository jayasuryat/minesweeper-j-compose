package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.component.ZoomableContent
import com.jayasuryat.minesweeperui.composable.util.dp
import com.jayasuryat.minesweeperui.composable.util.floatValue
import com.jayasuryat.minesweeperengine.model.grid.MineGrid as MineGridData

@Composable
public fun Minefield(
    modifier: Modifier,
    mineGrid: MineGridData,
) {

    LogCompositions(name = "inside Minefield")

    ZoomableContent { zoomModifier: Modifier ->

        LogCompositions(name = "inside ZoomableContent")

        BoxWithConstraints(
            modifier = modifier
                .padding(16.dp)
                .then(zoomModifier)
        ) {

            LogCompositions(name = "inside MineGrid")

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