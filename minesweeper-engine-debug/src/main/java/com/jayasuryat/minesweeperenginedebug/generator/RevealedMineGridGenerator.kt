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
