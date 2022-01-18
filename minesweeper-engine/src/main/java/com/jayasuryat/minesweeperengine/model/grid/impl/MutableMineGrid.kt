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
package com.jayasuryat.minesweeperengine.model.grid.impl

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

internal data class MutableMineGrid(
    override val gridSize: GridSize,
    override val totalMines: Int,
    private val mutableCells: MutableList<MutableList<RawCell>> = mutableListOf(),
) : Grid {

    override val cells: List<List<RawCell>> = mutableCells

    override operator fun get(position: Position): RawCell {
        return cells[position.row][position.column]
    }

    override fun getOrNull(position: Position): RawCell? {
        return kotlin.runCatching { get(position) }.getOrNull()
    }

    operator fun <T : RawCell> set(position: Position, value: T) {
        mutableCells[position.row][position.column] = value
    }

    companion object {

        fun from(grid: Grid): MutableMineGrid {

            return MutableMineGrid(
                gridSize = grid.gridSize,
                totalMines = grid.totalMines,
                mutableCells = grid.cells.map { it.toMutableList() }.toMutableList(),
            )
        }
    }
}
