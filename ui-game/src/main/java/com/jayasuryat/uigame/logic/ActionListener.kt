package com.jayasuryat.uigame.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.getCurrentGrid
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.uigame.feedback.MusicManager
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
internal class ActionListener(
    private val statefulGrid: StatefulGrid,
    private val minefieldController: MinefieldController,
    private val coroutineScope: CoroutineScope,
    private val musicManager: MusicManager,
) : MinefieldActionsListener {

    private val _gameState: MutableState<GameState> = mutableStateOf(GameState.Idle)
    val gameState: State<GameState> = _gameState

    private val _progress: MutableState<GameProgress> = mutableStateOf(statefulGrid.getProgress())
    val gameProgress: State<GameProgress> = _progress

    override fun action(action: MinefieldAction) {
        coroutineScope.launch {
            handleAction(action = action)
        }
    }

    private suspend fun handleAction(action: MinefieldAction) {

        val event: MinefieldEvent = minefieldController.onAction(
            action = action,
            mineGrid = statefulGrid.getCurrentGrid(),
        )

        updateGameState(event = event)
        updateGridForEvent(event = event)
        updateGameProgress(statefulGrid = statefulGrid)
    }

    private suspend fun updateGridForEvent(event: MinefieldEvent) {

        when (event) {

            is MinefieldEvent.OnCellsUpdated -> {

                statefulGrid.updateCellsWith(
                    updatedCells = event.updatedCells,
                    onEach = { _, newCell ->

                        when (newCell) {
                            is RawCell.RevealedCell -> {
                                if (newCell.cell is MineCell.ValuedCell) musicManager.pop()
                            }
                            is RawCell.UnrevealedCell.FlaggedCell -> musicManager.affirmative()
                            is RawCell.UnrevealedCell.UnFlaggedCell -> musicManager.cancel()
                        }

                        delay(30L)
                    }
                )
            }

            is MinefieldEvent.OnGameOver -> {

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedEmptyCells,
                    onEach = { _, _ -> delay(30L) }
                )

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedValueCells,
                    onEach = { _, _ -> delay(30L) }
                )

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedMineCells,
                    onEach = { _, _ -> delay(30L) }
                )
            }

            is MinefieldEvent.OnGameComplete -> {

                statefulGrid.updateCellsWith(
                    updatedCells = event.updatedCells,
                )
            }

        }.exhaustive
    }

    private fun updateGameState(event: MinefieldEvent) {
        val newState = resolveGameState(event = event) ?: return
        _gameState.value = newState
    }

    private fun updateGameProgress(statefulGrid: StatefulGrid) {
        val progress = statefulGrid.getCurrentGrid().getProgress()
        _progress.value = progress
    }

    private fun StatefulGrid.getProgress(): GameProgress =
        this.getCurrentGrid().getProgress()

    private fun Grid.getProgress(): GameProgress {

        val flaggedCount = this.cells.sumOf { row ->
            row.count { cell ->
                cell is RawCell.UnrevealedCell.FlaggedCell
            }
        }

        return GameProgress(
            totalMinesCount = this.totalMines,
            flaggedMinesCount = flaggedCount,
        )
    }

    private fun resolveGameState(event: MinefieldEvent): GameState? {

        val currentState = _gameState.value

        val state = when (event) {

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