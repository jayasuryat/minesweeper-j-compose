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
package com.jayasuryat.minesweeperengine.controller.impl.handler.helper

import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ValueNeighbourCalculator {

    suspend fun getAllValueNeighbours(
        cell: MineCell.ValuedCell.EmptyCell,
        grid: Grid,
    ): List<RawCell.RevealedCell> = withContext(Dispatchers.Default) {
        getAllNeighbouringValueCell(
            cell = cell,
            grid = grid,
        )
    }

    private fun getAllNeighbouringValueCell(
        cell: MineCell.ValuedCell.EmptyCell,
        grid: Grid,
    ): List<RawCell.RevealedCell> {

        val visitedCells: MutableList<MineCell.ValuedCell.EmptyCell> = mutableListOf()

        fun MineCell.ValuedCell.EmptyCell.getX3EmptyCells(
            grid: Grid,
        ): List<RawCell.RevealedCell> {

            if (visitedCells.contains(this)) return listOf()
            visitedCells.add(this)

            val xRow = this.position.row
            val xColumn = this.position.column

            val cells = ((xRow - 1)..(xRow + 1)).map { row ->
                ((xColumn - 1)..(xColumn + 1)).map { column ->
                    Position(row = row, column = column)
                }
            }.flatten().mapNotNull { position ->
                grid.getOrNull(position)
            }

            if (cells.isEmpty()) return listOf()

            val neighbours: MutableList<RawCell.RevealedCell> = mutableListOf()

            cells.forEach { cell ->

                when (cell) {
                    is RawCell.RevealedCell -> Unit
                    is RawCell.UnrevealedCell.FlaggedCell -> Unit
                    is RawCell.UnrevealedCell.UnFlaggedCell -> {
                        val revealed = cell.asRevealed()
                        when (val revealedCell = revealed.cell) {
                            is MineCell.Mine -> Unit
                            is MineCell.ValuedCell.Cell -> neighbours.add(revealed)
                            is MineCell.ValuedCell.EmptyCell -> {
                                val recursiveNeighbours = revealedCell.getX3EmptyCells(grid)
                                neighbours.add(revealed)
                                neighbours.addAll(recursiveNeighbours)
                            }
                        }
                    }
                }
            }

            return neighbours.distinctBy { it.position }
        }

        return cell.getX3EmptyCells(grid)
    }
}
