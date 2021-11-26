package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.util.mutate
import com.jayasuryat.util.exhaustive

internal class CellFlagger : ActionHandler<MinefieldAction.OnCellLongPressed> {

    override suspend fun onAction(
        action: MinefieldAction.OnCellLongPressed,
        grid: Grid,
    ): MinefieldEvent {

        val updatedCell = when (action.cell) {

            is RawCell.UnrevealedCell.FlaggedCell -> action.cell.asUnFlagged()

            is RawCell.UnrevealedCell.UnFlaggedCell -> action.cell.asFlagged()

        }.exhaustive

        val isGameComplete = isGameComplete(
            grid = grid,
            updatedCell = updatedCell,
        )

        return if (isGameComplete) MinefieldEvent.OnGameComplete(updatedCells = listOf(updatedCell))
        else MinefieldEvent.OnCellsUpdated(updatedCells = listOf(updatedCell))
    }

    private fun isGameComplete(
        grid: Grid,
        updatedCell: RawCell,
    ): Boolean {

        val modGrid = grid.mutate { this[updatedCell.position] = updatedCell }

        val totalMineCount = grid.totalMines

        var totalFlaggedCount = 0
        var correctFlaggedCount = 0

        modGrid.cells.flatten().forEach { cell ->

            if (cell is RawCell.UnrevealedCell.FlaggedCell) {

                totalFlaggedCount++
                if (totalFlaggedCount > totalMineCount) return false
                if (cell.asRevealed().cell is MineCell.Mine) correctFlaggedCount++
            }
        }

        return correctFlaggedCount == totalMineCount
    }
}