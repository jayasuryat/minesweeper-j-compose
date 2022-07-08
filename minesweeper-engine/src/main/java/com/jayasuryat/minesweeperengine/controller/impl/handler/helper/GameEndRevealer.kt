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
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GameEndRevealer(
    private val radiallySorter: RadiallySorter,
) {

    suspend fun revealAllCells(
        grid: Grid,
        startPosition: Position,
    ): MinefieldEvent = withContext(Dispatchers.Default) {
        revealCellsByType(
            grid = grid,
            startPosition = startPosition,
        )
    }

    private fun revealCellsByType(
        grid: Grid,
        startPosition: Position,
    ): MinefieldEvent {

        val unrevealedCells = grid.cells.flatten().mapNotNull { cell ->
            when (cell) {
                is RawCell.RevealedCell -> null
                is RawCell.UnrevealedCell.FlaggedCell -> cell.asRevealed()
                is RawCell.UnrevealedCell.UnFlaggedCell -> cell.asRevealed()
            }
        }

        val updatedCells = radiallySorter.sortRadiallyOut(
            startingPosition = startPosition,
            cells = unrevealedCells,
        )

        return MinefieldEvent.OnGameOver(
            updatedCells = updatedCells,
        )
    }
}
