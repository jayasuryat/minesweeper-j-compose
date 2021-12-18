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
package com.jayasuryat.uigame.logic

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

internal class EmptyGridGenerator {

    fun generateEmptyGrid(
        gridSize: GridSize,
        mineCount: Int,
    ): Grid = generateFor(
        gridSize = gridSize,
        mineCount = mineCount,
    )

    private fun generateFor(
        gridSize: GridSize,
        mineCount: Int,
    ): Grid {

        val rows = gridSize.rows
        val columns = gridSize.columns

        return EmptyGrid(
            gridSize = gridSize,
            totalMines = mineCount,
            cells = (0 until rows).map { row ->
                (0 until columns).map { column ->
                    val position = Position(row = row, column = column)
                    val cell = MineCell.ValuedCell.EmptyCell(position = position)
                    RawCell.UnrevealedCell.UnFlaggedCell(cell = cell)
                }
            },
        )
    }

    @Stable
    private data class EmptyGrid(
        override val gridSize: GridSize,
        override val totalMines: Int,
        override val cells: List<List<RawCell>>,
    ) : Grid {

        override operator fun get(position: Position): RawCell {
            return cells[position.row][position.column]
        }

        override fun getOrNull(position: Position): RawCell? {
            return kotlin.runCatching { get(position) }.getOrNull()
        }
    }
}
