package com.jayasuryat.minesweeperengine.controller.impl.handler

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