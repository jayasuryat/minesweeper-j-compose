package com.jayasuryat.minesweeperengine.controller.impl

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.impl.handler.CellFlagger
import com.jayasuryat.minesweeperengine.controller.impl.handler.CellRevealer
import com.jayasuryat.minesweeperengine.controller.impl.handler.GridRevealer
import com.jayasuryat.minesweeperengine.controller.impl.handler.ValueCellRevealer
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public class GameController(
    private val cellReveler: ActionHandler<MinefieldAction.OnCellClicked>,
    private val cellFlagger: ActionHandler<MinefieldAction.OnCellLongPressed>,
    private val valueCellReveler: ActionHandler<MinefieldAction.OnValueCellClicked>,
) : MinefieldController {

    override suspend fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent = withContext(Dispatchers.Default) {
        reduceActionToEvent(
            action = action,
            mineGrid = mineGrid
        )
    }

    private suspend fun reduceActionToEvent(
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
            val gridRevealer = GridRevealer()
            return GameController(
                cellReveler = CellRevealer(gridRevealer = gridRevealer),
                cellFlagger = CellFlagger(),
                valueCellReveler = ValueCellRevealer(gridRevealer = gridRevealer),
            )
        }
    }
}