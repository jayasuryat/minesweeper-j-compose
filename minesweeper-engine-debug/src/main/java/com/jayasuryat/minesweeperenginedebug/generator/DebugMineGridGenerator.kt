package com.jayasuryat.minesweeperenginedebug.generator

import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperenginedebug.model.DebugMineGrid

public class DebugMineGridGenerator : GridGenerator {

    override suspend fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): Grid {

        val cells: MutableList<MutableList<RawCell>> = (0 until gridSize.rows).map { row ->

            (0 until gridSize.columns).map { column ->

                val index = column + (row * gridSize.columns)

                RawCell.RevealedCell(
                    cell = MineCell.ValuedCell.Cell(
                        value = index,
                        position = Position(
                            row = row,
                            column = column
                        )
                    )
                ) as RawCell
            }.toMutableList()

        }.toMutableList()

        return DebugMineGrid(
            gridSize = gridSize,
            totalMines = mineCount,
            mutableCells = cells
        )
    }
}