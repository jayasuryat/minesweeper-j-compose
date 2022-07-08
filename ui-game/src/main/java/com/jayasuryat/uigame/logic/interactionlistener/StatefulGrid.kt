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
package com.jayasuryat.uigame.logic.interactionlistener

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.structuralEqualityPolicy
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.impl.MineGrid
import com.jayasuryat.minesweeperui.model.DisplayCell

@Stable
internal class StatefulGrid private constructor(
    val gridSize: GridSize,
    val totalMines: Int,
    private val mutableRawCells: MutableList<MutableList<RawCell>>,
    private val mutableDisplayCells: List<List<MutableDisplayCell>>,
) {

    val displayCells: List<List<DisplayCell>> = mutableDisplayCells

    operator fun get(position: Position): RawCell = getRawCell(position)

    fun getOrNull(position: Position): RawCell? {
        return kotlin.runCatching { getRawCell(position) }.getOrNull()
    }

    fun updateCellsWith(
        updatedCells: List<RawCell>,
    ) {
        updatedCells.forEach { cell -> updateCell(cell) }
    }

    suspend fun updateCellsWith(
        updatedCells: List<RawCell>,
        onEach: suspend (oldCell: RawCell, newCell: RawCell) -> Unit,
    ) {

        updatedCells.forEach { newCell ->
            val oldCell = getRawCell(newCell.position)
            updateCell(newCell)
            onEach(oldCell, newCell)
        }
    }

    fun getCurrentGrid(): Grid = MineGrid(
        gridSize = this.gridSize,
        totalMines = this.totalMines,
        cells = this.mutableRawCells
    )

    private fun updateCell(value: RawCell) {
        setMutableRawCell(value.position, value)
        getMutableDisplayCell(value.position).cellState.value = DisplayCell.Cell.from(value)
    }

    private fun getMutableDisplayCell(position: Position): MutableDisplayCell {
        return mutableDisplayCells[position.row][position.column]
    }

    private fun getRawCell(position: Position): RawCell {
        return mutableRawCells[position.row][position.column]
    }

    private fun setMutableRawCell(position: Position, value: RawCell) {
        mutableRawCells[position.row][position.column] = value
    }

    internal companion object {

        internal fun from(grid: Grid): StatefulGrid {

            val mutableRawCells: MutableList<MutableList<RawCell>> = grid.cells.map { row ->
                row.toMutableList()
            }.toMutableList()

            val mutableDisplayCells: List<List<MutableDisplayCell>> = grid.cells.map { row ->
                row.map { cell ->

                    val displayCell = DisplayCell.Cell.from(cell)

                    MutableDisplayCell(
                        position = cell.position,
                        cellState = mutableStateOf(
                            value = displayCell,
                            policy = structuralEqualityPolicy(),
                        )
                    )
                }
            }

            return StatefulGrid(
                gridSize = grid.gridSize,
                totalMines = grid.totalMines,
                mutableDisplayCells = mutableDisplayCells,
                mutableRawCells = mutableRawCells,
            )
        }
    }
}
