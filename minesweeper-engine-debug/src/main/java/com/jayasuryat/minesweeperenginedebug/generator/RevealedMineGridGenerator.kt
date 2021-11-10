package com.jayasuryat.minesweeperenginedebug.generator

import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.MineGrid


public class RevealedMineGridGenerator(
    private val backingGenerator: GridGenerator,
) : GridGenerator {

    override fun generateGrid(
        gridSize: GridSize,
        starCell: Position,
        mineCount: Int,
    ): MineGrid {

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
                }
            }
        }

        return grid.copy(
            cells = modCells
        )
    }
}