/*
 * Copyright 2022 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayasuryat.minesweeperui.grid

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.minesweeperui.cell.RawCell
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.dp
import com.jayasuryat.util.floatValue

@Composable
internal fun MineGrid(
    modifier: Modifier,
    horizontalPadding: Dp = 16.dp,
    gridInfo: GridLayoutInformation,
    actionListener: CellInteractionListener,
) {

    BoxWithConstraints(
        modifier = modifier,
    ) {

        val width = maxWidth
        val gridSize = gridInfo.gridSize

        val cellSize = getCellSize(
            gridSize = gridSize,
            width = width - (horizontalPadding * 2),
        )

        Grid(
            parentHeight = maxHeight,
            horizontalPadding = horizontalPadding,
            gridInfo = gridInfo,
            actionListener = actionListener,
            cellSize = cellSize,
        )
    }
}

@Composable
private fun Grid(
    parentHeight: Dp,
    horizontalPadding: Dp,
    gridInfo: GridLayoutInformation,
    actionListener: CellInteractionListener,
    cellSize: Float,
) {

    LogCompositions(name = "Grid")

    val centerOffset = (parentHeight / 2).floatValue() - (cellSize * gridInfo.gridSize.rows / 2)
    val paddingOffset = horizontalPadding.floatValue()

    gridInfo.cells.forEach { cellData ->

        val (cell: State<RawCell>, position: Position) = cellData
        val offset = position.asOffset(cellSize = cellSize)

        RawCell(
            modifier = Modifier
                .size(cellSize.dp())
                .graphicsLayer {
                    translationX = offset.x + paddingOffset
                    translationY = offset.y + centerOffset
                },
            cellState = cell,
            actionListener = actionListener,
        )
    }
}

@Composable
@Stable
@ReadOnlyComposable
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
