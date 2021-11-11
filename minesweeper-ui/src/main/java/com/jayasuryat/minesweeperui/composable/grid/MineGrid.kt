package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.util.dp
import com.jayasuryat.minesweeperui.composable.util.floatValue

@Composable
internal fun MineGrid(
    modifier: Modifier,
    mineGrid: Grid,
    actionListener: MinefieldActionsListener,
) {

    BoxWithConstraints(
        modifier = modifier,
    ) {

        val width = maxWidth

        val cellSize = getCellSize(
            gridSize = mineGrid.gridSize,
            width = width,
        )

        val centerOffset = (maxHeight / 2).floatValue() - (cellSize * mineGrid.gridSize.rows / 2)

        val overlap = 1f
        val overlappingCellSize = cellSize + overlap

        InverseClippedBox(
            parentSize = Size(width = maxWidth.floatValue(),
                height = maxHeight.floatValue()),
            contentSize = Size(
                width = cellSize * mineGrid.gridSize.columns,
                height = cellSize * mineGrid.gridSize.rows,
            ),
            padding = Size(width = 32f, height = 32f),
            innerInset = Size(width = 1f, height = 1f)
        )

        mineGrid.cells.flatten().forEach { cell ->

            val offset = cell.position.asOffset(cellSize = cellSize)

            RawCell(
                modifier = Modifier
                    .size(overlappingCellSize.dp())
                    .graphicsLayer {
                        translationX = offset.x
                        translationY = offset.y + centerOffset
                    },
                cell = cell,
                actionListener = actionListener,
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