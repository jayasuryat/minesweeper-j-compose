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

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.impl.handler.ValueNeighbourCalculator.getAllValueNeighbours
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive

internal class CellRevealer(
    private val gridRevealer: GridRevealer,
) : ActionHandler<MinefieldAction.OnCellClicked> {

    override suspend fun onAction(
        action: MinefieldAction.OnCellClicked,
        grid: Grid,
    ): MinefieldEvent {

        val revealed = action.cell.asRevealed()

        return when (val cell = revealed.cell) {

            is MineCell.ValuedCell.Cell -> {
                MinefieldEvent.OnCellsUpdated(updatedCells = listOf(revealed))
            }

            is MineCell.ValuedCell.EmptyCell -> {
                val updatedCells = cell.getAllValueNeighbours(grid = grid)
                MinefieldEvent.OnCellsUpdated(updatedCells = updatedCells)
            }

            is MineCell.Mine -> gridRevealer.revealAllCells(grid = grid)
        }.exhaustive
    }
}
