/*
 * Copyright 2021 Jaya Surya Thotapalli
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
package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GridRevealer {

    suspend fun revealAllCells(
        grid: Grid,
    ): MinefieldEvent = withContext(Dispatchers.Default) {
        revealCellsByType(
            grid = grid,
        )
    }

    private fun revealCellsByType(
        grid: Grid,
    ): MinefieldEvent {

        val allCells = grid.cells.flatten()

        val mineCells: MutableList<RawCell.RevealedCell> = mutableListOf()
        val valueCells: MutableList<RawCell.RevealedCell> = mutableListOf()
        val emptyCells: MutableList<RawCell.RevealedCell> = mutableListOf()

        allCells.forEach { cell ->

            val revealed = when (cell) {
                is RawCell.RevealedCell -> null
                is RawCell.UnrevealedCell -> cell.asRevealed()
            }.exhaustive

            revealed?.let {

                when (revealed.cell) {
                    is MineCell.Mine -> mineCells.add(revealed)
                    is MineCell.ValuedCell.Cell -> valueCells.add(revealed)
                    is MineCell.ValuedCell.EmptyCell -> emptyCells.add(revealed)
                }.exhaustive
            }
        }

        return MinefieldEvent.OnGameOver(
            revealedMineCells = mineCells,
            revealedEmptyCells = emptyCells,
            revealedValueCells = valueCells,
        )
    }
}

/*
private fun revealCellsInsideOut(
        startCell: RawCell,
        grid: Grid,
    ): MinefieldEvent {

        fun getRevealedCell(row: Int, column: Int): RawCell.RevealedCell? {
            val position = Position(
                row = row,
                column = column,
            )
            return when (val cell = grid.getOrNull(position = position)) {
                null -> null
                is RawCell.RevealedCell -> null
                is RawCell.UnrevealedCell -> cell.asRevealed()
            }
        }

        val cRow = startCell.position.row
        val cCol = startCell.position.column

        val maxIterations = maxOf((grid.gridSize.rows - cRow), (grid.gridSize.columns - cCol))

        val cells: MutableList<RawCell.RevealedCell> = mutableListOf()

        repeat(maxIterations) { iteration ->

            val startCol = cCol - iteration
            val endCol = cCol + iteration
            val startRow = cRow - iteration
            val endRow = cRow + iteration

            // Left to Right
            for (col in (startCol + 1)..endCol) {
                getRevealedCell(row = startRow, column = col)?.let { modCell -> cells.add(modCell) }
            }

            // Top to Bottom
            for (row in startRow..endRow) {
                getRevealedCell(row = row, column = endCol)?.let { modCell -> cells.add(modCell) }
            }

            // Right to Left
            for (col in endCol downTo startCol) {
                getRevealedCell(row = endRow, column = col)?.let { modCell -> cells.add(modCell) }
            }

            // Bottom to Top
            for (row in endRow downTo startRow) {
                getRevealedCell(row = row, column = startCol)?.let { modCell -> cells.add(modCell) }
            }
        }

        return MinefieldEvent.OnCellsUpdated(
            updatedCells = cells
                .distinctBy { it.position },
        )
    }
*/
