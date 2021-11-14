package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.impl.handler.ValueNeighbourCalculator.getAllValueNeighbours
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.util.mutate
import com.jayasuryat.util.exhaustive

internal class CellReveler : ActionHandler<MinefieldAction.OnCellClicked> {

    override fun onAction(
        action: MinefieldAction.OnCellClicked,
        grid: Grid,
    ): MinefieldEvent {

        val revealed = action.cell.asRevealed()

        return when (val cell = revealed.cell) {

            is MineCell.ValuedCell.Cell -> {
                val updatedGrid = grid.mutate { this[action.cell.position] = revealed }
                MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
            }

            is MineCell.ValuedCell.EmptyCell -> {
                val updatedGrid = cell.revealNeighbouringCells(grid = grid)
                MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
            }

            is MineCell.Mine -> MinefieldEvent.OnGameOver

        }.exhaustive
    }

    private fun MineCell.ValuedCell.EmptyCell.revealNeighbouringCells(grid: Grid): Grid {

        val cells = this.getAllValueNeighbours(grid = grid)

        return grid.mutate {
            cells.forEach { cell -> this[cell.position] = cell }
        }
    }
}