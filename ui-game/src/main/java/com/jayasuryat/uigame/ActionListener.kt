package com.jayasuryat.uigame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.getCurrentGrid
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
internal class ActionListener(
    private val statefulGrid: StatefulGrid,
    private val minefieldController: MinefieldController,
    private val coroutineScope: CoroutineScope,
) : MinefieldActionsListener {

    private val _gameState: MutableState<GameState> = mutableStateOf(GameState.Idle)
    val gameState: State<GameState> = _gameState

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

        updateGameState(state)

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

            is MinefieldEvent.OnGameComplete -> {

                statefulGrid.updateCellsWith(
                    updatedCells = state.updatedCells,
                )
            }

        }.exhaustive
    }

    private fun updateGameState(event: MinefieldEvent) {

        val newState = resolveGameState(event = event) ?: return
        _gameState.value = newState
    }

    private fun resolveGameState(event: MinefieldEvent): GameState? {

        val currentState = _gameState.value

        val state = when (event) {

            is MinefieldEvent.OnGridUpdated,
            is MinefieldEvent.OnCellsUpdated,
            -> {
                if (currentState is GameState.Idle) GameState.GameStarted.now() else null
            }

            is MinefieldEvent.OnGameOver -> {
                GameState.GameEnded.GameOver.now()
            }

            is MinefieldEvent.OnGameComplete -> {
                require(currentState is GameState.GameStarted) { "Game cannot complete without being in started state" }
                GameState.GameEnded.GameCompleted.now(startTime = currentState.startTime)
            }
        }

        return state
    }
}