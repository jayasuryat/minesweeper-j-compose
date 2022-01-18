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
package com.jayasuryat.minesweeperengine.state.impl

import androidx.compose.runtime.*
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.state.StatefulGrid

@Stable
internal class MutableStatefulGrid(
    override val gridSize: GridSize,
    override val totalMines: Int,
    private val mutableCells: List<List<MutableState<RawCell>>> = getEmptyCellsList(gridSize),
) : StatefulGrid {

    override val cells: List<List<State<RawCell>>> = mutableCells

    private fun getMutableCell(position: Position): MutableState<RawCell> {
        return mutableCells[position.row][position.column]
    }

    override fun get(position: Position): State<RawCell> = getMutableCell(position)

    override fun getOrNull(position: Position): State<RawCell>? {
        return kotlin.runCatching { getMutableCell(position) }.getOrNull()
    }

    override fun updateCellsWith(updatedCells: List<RawCell>) {
        updatedCells.forEach { cell ->
            getMutableCell(cell.position).value = cell
        }
    }

    override suspend fun updateCellsWith(
        updatedCells: List<RawCell>,
        onEach: suspend (oldCell: RawCell, newCell: RawCell) -> Unit,
    ) {
        updatedCells.forEach { cell ->
            val oldCell = getMutableCell(cell.position).value
            getMutableCell(cell.position).value = cell
            onEach(oldCell, cell)
        }
    }

    internal companion object {

        private fun emptyCell(
            row: Int,
            column: Int,
        ): MineCell.ValuedCell.EmptyCell {
            val position = Position(row = row, column = column)
            return MineCell.ValuedCell.EmptyCell(position = position)
        }

        private fun getEmptyCellsList(
            gridSize: GridSize,
        ): List<List<MutableState<RawCell>>> {

            val rows = gridSize.rows
            val columns = gridSize.columns

            val cells: List<List<MutableState<RawCell>>> = (0 until rows).map { row ->
                (0 until columns).map { column ->
                    val cell = emptyCell(row = row, column = column)
                    val rawCell = RawCell.UnrevealedCell.UnFlaggedCell(cell)
                    mutableStateOf(
                        value = rawCell,
                        policy = structuralEqualityPolicy(),
                    )
                }
            }
            return cells
        }

        internal fun from(grid: Grid): MutableStatefulGrid {

            val cells: List<List<MutableState<RawCell>>> = grid.cells.map { row ->
                row.map { rawCell ->
                    mutableStateOf(
                        value = rawCell,
                        policy = structuralEqualityPolicy(),
                    )
                }
            }

            return MutableStatefulGrid(
                gridSize = grid.gridSize,
                totalMines = grid.totalMines,
                mutableCells = cells,
            )
        }
    }
}
