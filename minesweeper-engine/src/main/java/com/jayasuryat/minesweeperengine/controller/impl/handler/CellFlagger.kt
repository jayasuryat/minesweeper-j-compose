package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.util.mutate
import com.jayasuryat.util.exhaustive

internal class CellFlagger : ActionHandler<MinefieldAction.OnCellLongPressed> {

    override fun onAction(
        action: MinefieldAction.OnCellLongPressed,
        grid: Grid,
    ): MinefieldEvent {

        val updatedGrid = when (action.cell) {

            is RawCell.UnrevealedCell.FlaggedCell -> grid.mutate {
                this[action.cell.position] = action.cell.asUnFlagged()
            }

            is RawCell.UnrevealedCell.UnFlaggedCell -> grid.mutate {
                this[action.cell.position] = action.cell.asFlagged()
            }

        }.exhaustive

        return MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
    }
}