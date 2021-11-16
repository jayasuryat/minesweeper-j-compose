package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.impl.handler.ValueNeighbourCalculator.getAllValueNeighbours
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive

internal class CellReveler : ActionHandler<MinefieldAction.OnCellClicked> {

    override fun onAction(
        action: MinefieldAction.OnCellClicked,
        grid: Grid,
    ): MinefieldEvent {

        val revealed = action.cell.asRevealed()

        return when (val cell = revealed.cell) {

            is MineCell.ValuedCell.Cell -> {
                MinefieldEvent.OnCellsUpdated(updatedCells = listOf(revealed))
            }

            is MineCell.ValuedCell.EmptyCell -> {
                val updatedCells = cell.getAllValueNeighbours(grid = grid)
                MinefieldEvent.OnCellsUpdated(updatedCells = updatedCells)
            }

            is MineCell.Mine -> MinefieldEvent.OnGameOver

        }.exhaustive
    }
}