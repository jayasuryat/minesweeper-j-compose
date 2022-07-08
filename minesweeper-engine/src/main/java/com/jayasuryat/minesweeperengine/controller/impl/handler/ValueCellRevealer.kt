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
package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.impl.handler.helper.GameEndRevealer
import com.jayasuryat.minesweeperengine.controller.impl.handler.helper.GameSuccessEvaluator
import com.jayasuryat.minesweeperengine.controller.impl.handler.helper.RadiallySorter
import com.jayasuryat.minesweeperengine.controller.impl.handler.helper.ValueNeighbourCalculator
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.util.mutate

internal class ValueCellRevealer(
    private val gameEndRevealer: GameEndRevealer,
    private val radiallySorter: RadiallySorter,
    private val successEvaluator: GameSuccessEvaluator,
    private val neighbourCalculator: ValueNeighbourCalculator,
) : ActionHandler<MinefieldAction.OnValueCellClicked> {

    override suspend fun onAction(
        action: MinefieldAction.OnValueCellClicked,
        grid: Grid,
    ): MinefieldEvent {

        val cell = action.cell

        val neighbours = cell.getX3Neighbours(grid = grid)

        val flagCount = neighbours.count { it is RawCell.UnrevealedCell.FlaggedCell }
        val mineCount = neighbours.count {
            val countCell = when (it) {
                is RawCell.RevealedCell -> it.cell
                is RawCell.UnrevealedCell.FlaggedCell -> it.asRevealed().cell
                is RawCell.UnrevealedCell.UnFlaggedCell -> it.asRevealed().cell
            }
            countCell is MineCell.Mine
        }

        if (flagCount != mineCount) return MinefieldEvent.OnCellsUpdated(updatedCells = listOf())

        val areFlagsCorrect = neighbours.filterIsInstance<RawCell.UnrevealedCell.FlaggedCell>()
            .all { flaggedCell -> flaggedCell.asRevealed().cell is MineCell.Mine }

        if (!areFlagsCorrect) {
            return gameEndRevealer.revealAllCells(
                grid = grid,
                startPosition = action.cell.position,
            )
        }

        val updatedCells = neighbours.map {
            when (it) {
                is RawCell.RevealedCell -> emptyList()
                is RawCell.UnrevealedCell.FlaggedCell -> emptyList()
                is RawCell.UnrevealedCell.UnFlaggedCell -> {

                    val revealed = it.asRevealed()
                    when (val valueCell = revealed.cell) {
                        is MineCell.Mine -> throw IllegalArgumentException("This shouldn't be possible")
                        is MineCell.ValuedCell.Cell -> listOf(revealed)
                        is MineCell.ValuedCell.EmptyCell ->
                            neighbourCalculator
                                .getAllValueNeighbours(cell = valueCell, grid = grid)
                    }
                }
            }
        }.flatten().distinctBy { it.position }

        val isGameComplete = isGameComplete(
            grid = grid,
            updatedCells = updatedCells
        )

        val event = if (isGameComplete) {

            MinefieldEvent.OnGameComplete(updatedCells = updatedCells)
        } else {

            val sortedCells = radiallySorter.sortRadiallyOut(
                startingPosition = action.cell.position,
                cells = updatedCells,
            )
            MinefieldEvent.OnCellsUpdated(updatedCells = sortedCells)
        }

        return event
    }

    private fun MineCell.ValuedCell.Cell.getX3Neighbours(
        grid: Grid,
    ): List<RawCell> {

        val cRow = this.position.row
        val cCol = this.position.column

        return ((cRow - 1)..(cRow + 1)).map { row ->
            ((cCol - 1)..(cCol + 1)).map { col ->
                grid.getOrNull(position = Position(row = row, column = col))
            }
        }.flatten().filterNotNull()
    }

    private fun isGameComplete(
        grid: Grid,
        updatedCells: List<RawCell>,
    ): Boolean {
        val modGrid = grid.mutate {
            updatedCells.forEach { cell -> this[cell.position] = cell }
        }
        return successEvaluator.isGameComplete(modGrid)
    }
}
