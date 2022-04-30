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
package com.jayasuryat.minesweeperjc.data.mapper.impl

import com.jayasuryat.data.model.Cell
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.impl.MineGrid
import com.jayasuryat.minesweeperjc.data.mapper.definition.GridReadMapper
import com.jayasuryat.data.model.Grid as DGrid
import com.jayasuryat.minesweeperengine.model.grid.Grid as GGrid

class GridReadMapperImpl : GridReadMapper {

    override fun map(
        input: DGrid,
    ): GGrid {

        val grid = input.grid
        val gridSize = grid.validateAndGetGridSize()

        val mineCount = input.grid
            .flatten()
            .count { cell -> cell.value == MappingConstants.CELL_MINE_VALUE }

        val cells = grid.map { row ->

            row.map { cell ->

                val position = cell.position.toPosition()

                val mineCell = when (cell.value) {
                    MappingConstants.CELL_MINE_VALUE -> MineCell.Mine(position = position)
                    MappingConstants.CELL_EMPTY_VALUE -> MineCell.ValuedCell.EmptyCell(position = position)
                    in MappingConstants.CELL_VALUE_RANGE -> MineCell.ValuedCell.Cell(
                        position = position,
                        value = cell.value,
                    )
                    else -> error("Invalid cell value : ${cell.value}")
                }

                val rawCell = when {

                    cell.revealed -> RawCell.RevealedCell(cell = mineCell)

                    cell.flagged -> RawCell.UnrevealedCell.FlaggedCell(cell = mineCell)

                    else -> RawCell.UnrevealedCell.UnFlaggedCell(cell = mineCell)
                }

                rawCell
            }
        }

        return MineGrid(
            gridSize = gridSize,
            totalMines = mineCount,
            cells = cells,
        )
    }

    private fun List<List<Cell>>.validateAndGetGridSize(): GridSize {

        val grid = this
        val rows = grid.size
        val columns = grid.first().size

        val columnSizeIntegrity = grid.all { row -> row.size == columns }
        require(columnSizeIntegrity)

        return GridSize(
            rows = rows,
            columns = columns,
        )
    }

    private fun Cell.Companion.Position.toPosition(): Position = Position(
        row = row,
        column = column,
    )
}
