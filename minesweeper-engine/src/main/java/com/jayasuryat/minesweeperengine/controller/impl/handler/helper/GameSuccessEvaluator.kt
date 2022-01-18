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

import android.util.Log
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

internal class GameSuccessEvaluator {

    fun isGameComplete(
        grid: Grid,
    ): Boolean {

        val totalCount = grid.gridSize.rows * grid.gridSize.columns
        val nonMineCellsCount = totalCount - grid.totalMines

        val revealedCellsCount = grid.cells.flatten().count { it is RawCell.RevealedCell }
        Log.d("Im alive", "Im alive, (revealedCellsCount) $revealedCellsCount == $nonMineCellsCount (nonMineCellsCount)")
        return revealedCellsCount == nonMineCellsCount
    }
}
