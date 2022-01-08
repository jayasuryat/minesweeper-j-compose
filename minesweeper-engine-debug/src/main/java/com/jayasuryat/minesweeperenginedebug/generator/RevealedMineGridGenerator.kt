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
package com.jayasuryat.minesweeperenginedebug.generator

import com.jayasuryat.minesweeperengine.gridgenerator.GridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperenginedebug.model.DebugMineGrid

public class RevealedMineGridGenerator(
    private val backingGenerator: GridGenerator,
) : GridGenerator {

    override suspend fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): Grid {

        val grid = backingGenerator.generateGrid(
            gridSize = gridSize,
            starCell = starCell,
            mineCount = mineCount,
        )

        val modCells = grid.cells.map { row ->
            row.map { rawCell ->
                when (rawCell) {
                    is RawCell.RevealedCell -> rawCell
                    is RawCell.UnrevealedCell -> rawCell.asRevealed()
                } as RawCell
            }.toMutableList()
        }.toMutableList()

        return DebugMineGrid(
            gridSize = grid.gridSize,
            totalMines = grid.totalMines,
            mutableCells = modCells,
        )
    }
}
