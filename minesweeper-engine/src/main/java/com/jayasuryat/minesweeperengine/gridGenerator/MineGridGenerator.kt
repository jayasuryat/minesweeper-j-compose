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
package com.jayasuryat.minesweeperengine.gridgenerator

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.impl.MineGrid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

private typealias MutableCell2DList = MutableList<MutableList<MineCell>>

public class MineGridGenerator : GridGenerator {

    override suspend fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): Grid = withContext(Dispatchers.Default) {
        generateFor(
            gridSize = gridSize,
            starCell = starCell,
            mineCount = mineCount,
        )
    }

    private fun generateFor(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): MineGrid {

        val rows = gridSize.rows
        val columns = gridSize.columns

        val length = rows * columns

        val ignoredIndices = starCell
            .get3xBlockCellIndexes(gridSize = gridSize)
            .toSet()

        val cellIndexes = (0 until length)
            .toMutableList()
            .apply { removeAll(ignoredIndices) }

        require(cellIndexes.size >= mineCount) { "Not enough room to generate grid" }

        val mineIndexes = (0 until mineCount).map {
            val randomPosition = Random.nextInt(cellIndexes.size)
            val mine = cellIndexes[randomPosition]
            cellIndexes.removeAt(randomPosition)
            mine
        }

        val mineCells: List<MineCell.Mine> = mineIndexes.map { position ->
            MineCell.Mine(
                position = Position(
                    row = position / columns,
                    column = position % columns,
                )
            )
        }

        val emptyCells: MutableCell2DList = MutableList(rows) { row ->
            MutableList(columns) { column ->
                MineCell.ValuedCell.EmptyCell(
                    position = Position(
                        row = row,
                        column = column,
                    )
                )
            }
        }

        val withMines = mineCells
            .mergeInto(destination = emptyCells)
            .fillNeighbouringMineCount()
            .wrapAsRawCells()

        return MineGrid(
            gridSize = gridSize,
            totalMines = mineCount,
            cells = withMines,
        )
    }

    private fun List<MineCell>.mergeInto(
        destination: MutableCell2DList,
    ): MutableCell2DList {

        this.forEach { mineCell ->
            val row = mineCell.position.row
            val column = mineCell.position.column
            destination[row][column] = mineCell
        }

        return destination
    }

    private fun MutableCell2DList.fillNeighbouringMineCount(): MutableCell2DList {

        fun MineCell.getMineCount(): Int {
            val row = this.position.row
            val column = this.position.column
            var count = 0
            for (i in (row - 1)..(row + 1)) {
                for (j in (column - 1)..(column + 1)) {
                    val cell = this@fillNeighbouringMineCount.getOrNull(i)?.getOrNull(j)
                    if (cell != null && cell is MineCell.Mine) count++
                }
            }
            return count
        }

        this.forEach { row ->
            row.forEach { cell ->

                if (cell !is MineCell.Mine) {

                    val newCell = when (val count = cell.getMineCount()) {
                        0 -> MineCell.ValuedCell.EmptyCell(position = cell.position)
                        else -> MineCell.ValuedCell.Cell(position = cell.position, value = count)
                    }
                    this[cell.position.row][cell.position.column] = newCell
                }
            }
        }

        return this
    }

    private fun MutableCell2DList.wrapAsRawCells(): List<List<RawCell>> {

        return this.map { row ->
            row.map { cell ->
                RawCell.UnrevealedCell.UnFlaggedCell(cell = cell)
            }
        }
    }

    private fun getIndex(
        row: Int,
        column: Int,
        gridSize: GridSize,
    ): Int {
        return (row * gridSize.columns) + column
    }

    private fun Position.get3xBlockCellIndexes(
        gridSize: GridSize,
    ): List<Int> {

        val currentRow = this.row
        val currentColumn = this.column

        val positions: MutableList<Int> = mutableListOf()

        for (row in (currentRow - 1)..(currentRow + 1)) {
            for (column in (currentColumn - 1)..(currentColumn + 1)) {

                val isValid = (0 until gridSize.rows).contains(row) &&
                    (0 until gridSize.columns).contains(column)

                if (isValid) {

                    val index = getIndex(
                        row = row,
                        column = column,
                        gridSize = gridSize,
                    )
                    positions.add(index)
                }
            }
        }

        return positions
    }
}
