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

import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GameEndRevealer {

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
