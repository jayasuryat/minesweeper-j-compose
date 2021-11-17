package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
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

        return MinefieldEvent.OnCellsUpdated(updatedCells = listOf(updatedCell))
    }
}