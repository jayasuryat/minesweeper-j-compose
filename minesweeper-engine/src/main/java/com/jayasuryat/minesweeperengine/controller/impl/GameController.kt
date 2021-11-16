package com.jayasuryat.minesweeperengine.controller.impl

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.impl.handler.CellFlagger
import com.jayasuryat.minesweeperengine.controller.impl.handler.CellReveler
import com.jayasuryat.minesweeperengine.controller.impl.handler.ValueCellReveler
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive

public class GameController(
    private val cellReveler: ActionHandler<MinefieldAction.OnCellClicked>,
    private val cellFlagger: ActionHandler<MinefieldAction.OnCellLongPressed>,
    private val valueCellReveler: ActionHandler<MinefieldAction.OnValueCellClicked>,
) : MinefieldController {

    override fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent = reduceActionToEvent(
        action = action,
        mineGrid = mineGrid
    )

    private fun reduceActionToEvent(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent {

        return when (action) {

            is MinefieldAction.OnCellClicked -> cellReveler.onAction(
                action = action,
                grid = mineGrid,
            )

            is MinefieldAction.OnValueCellClicked -> valueCellReveler.onAction(
                action = action,
                grid = mineGrid,
            )

            is MinefieldAction.OnCellLongPressed -> cellFlagger.onAction(
                action = action,
                grid = mineGrid,
            )

        }.exhaustive
    }

    public companion object {

        public fun getDefault(): GameController {
            return GameController(
                cellReveler = CellReveler(),
                cellFlagger = CellFlagger(),
                valueCellReveler = ValueCellReveler(),
            )
        }
    }
}