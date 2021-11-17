package com.jayasuryat.uigame

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.getCurrentGrid
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
internal class ActionListener(
    private val statefulGrid: StatefulGrid,
    private val minefieldController: MinefieldController,
    private val coroutineScope: CoroutineScope,
) : MinefieldActionsListener {

    override fun action(action: MinefieldAction) {
        coroutineScope.launch {
            handleAction(action = action)
        }
    }

    private suspend fun handleAction(action: MinefieldAction) {

        val state: MinefieldEvent = minefieldController.onAction(
            action = action,
            mineGrid = statefulGrid.getCurrentGrid(),
        )

        when (state) {

            is MinefieldEvent.OnGridUpdated -> {
                statefulGrid.updateCellsWith(updatedCells = state.mineGrid.cells.flatten())
            }

            is MinefieldEvent.OnCellsUpdated -> {

                statefulGrid.updateCellsWith(
                    updatedCells = state.updatedCells,
                    delayForEachCell = 30L,
                )
            }

            is MinefieldEvent.OnGameOver -> {

                statefulGrid.updateCellsWith(
                    updatedCells = state.revealedEmptyCells,
                    delayForEachCell = 30L,
                )

                statefulGrid.updateCellsWith(
                    updatedCells = state.revealedValueCells,
                    delayForEachCell = 30L,
                )

                statefulGrid.updateCellsWith(
                    updatedCells = state.revealedMineCells,
                    delayForEachCell = 30L,
                )
            }

        }.exhaustive
    }
}