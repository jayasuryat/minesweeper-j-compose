package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
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

    LogCompositions(name = "Minefield")

    ZoomableContent { zoomModifier: Modifier ->

        LogCompositions(name = "ZoomableContent")

        BoxWithConstraints(
            modifier = modifier
                .then(zoomModifier)
        ) {

            LogCompositions(name = "MineGrid")

            val width = maxWidth
            val height = maxHeight

            val cellSize = getCellSize(
                gridSize = mineGrid.gridSize,
                width = width,
                height = height,
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
private fun getCellSize(
    gridSize: GridSize,
    width: Dp,
    height: Dp,
): Float {
    val defCellWidth = width / gridSize.columns
    val defCellHeight = height / gridSize.rows
    return minOf(defCellWidth, defCellHeight).floatValue()
}

private fun Position.asOffset(
    cellSize: Float,
): Offset {

    val xOff = cellSize * this.column
    val yOff = cellSize * this.row
    return Offset(x = xOff, y = yOff)
}


private fun Offset.padded(padding: Float): Offset {
    val xOff = this.x - padding
    val yOff = this.y - padding
    return Offset(x = xOff, y = yOff)
}