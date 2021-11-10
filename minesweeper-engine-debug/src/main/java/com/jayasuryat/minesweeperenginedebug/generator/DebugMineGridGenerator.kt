package com.jayasuryat.minesweeperenginedebug.generator

import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.MineGrid


public class DebugMineGridGenerator : GridGenerator {

    override fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): MineGrid {

        val cells: List<List<RawCell.RevealedCell>> = (0 until gridSize.rows).map { row ->
            (0 until gridSize.columns).map { column ->

                val index = column + (row * gridSize.columns)

                RawCell.RevealedCell(
                    cell = MineCell.Cell(
                        value = index,
                        position = Position(
                            row = row,
                            column = column
                        )
                    )
                )
            }
        }

        return MineGrid(
            gridSize = gridSize,
            cells = cells
        )
    }
}