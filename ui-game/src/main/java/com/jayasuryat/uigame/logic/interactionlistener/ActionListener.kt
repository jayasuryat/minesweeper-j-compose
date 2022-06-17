/*
 * Copyright 2022 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayasuryat.uigame.logic.interactionlistener

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.gridgenerator.GridGenerator
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperui.action.CellInteraction
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.uigame.data.model.ToggleState
import com.jayasuryat.uigame.feedback.sound.MusicManager
import com.jayasuryat.uigame.feedback.vibration.VibrationManager
import com.jayasuryat.uigame.logic.model.GameProgress
import com.jayasuryat.uigame.logic.model.GameState
import com.jayasuryat.uigame.logic.model.InitialGrid
import com.jayasuryat.util.exhaustive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
internal class ActionListener(
    private val initialGrid: InitialGrid,
    private val minefieldController: MinefieldController,
    private val toggleState: State<ToggleState>,
    private val coroutineScope: CoroutineScope,
    private val musicManager: MusicManager,
    private val vibrationManager: VibrationManager,
    private val onStateChanged: (newState: GameState) -> Unit,
) : CellInteractionListener {

    internal val statefulGrid: StatefulGrid = StatefulGrid.from(initialGrid.grid)

    private val _gameState: MutableState<GameState> = mutableStateOf(initialGrid.getGameState())
    val gameState: State<GameState> = _gameState

    private val _progress: MutableState<GameProgress> = mutableStateOf(statefulGrid.getProgress())
    val gameProgress: State<GameProgress> = _progress

    override fun action(action: CellInteraction) {

        coroutineScope.launch {

            action.mapToAction()?.let { handleAction(it) }
        }
    }

    private suspend fun handleAction(action: MinefieldAction) {

        val event = if (isInIdleState()) {

            if (action !is MinefieldAction.OnCellRevealed) return

            require(initialGrid is InitialGrid.NewGrid) { "Game is in idle state while the grid is in progress" }

            handleFirstClick(
                action = action,
                parentGrid = statefulGrid,
                gridGenerator = initialGrid.backingGridGenerator,
            )
        } else {

            minefieldController.onAction(
                action = action,
                mineGrid = statefulGrid.getCurrentGrid(),
            )
        }

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
                                if (newCell.cell is MineCell.ValuedCell) {
                                    musicManager.pop()
                                    vibrationManager.pop()
                                }
                            }
                            is RawCell.UnrevealedCell.FlaggedCell -> musicManager.affirmative()
                            is RawCell.UnrevealedCell.UnFlaggedCell -> musicManager.cancel()
                        }

                        delay(DELAY_FOR_EACH_CELL)
                    }
                )
            }

            is MinefieldEvent.OnGameOver -> {

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedEmptyCells,
                    onEach = { _, _ -> delay(DELAY_FOR_EACH_CELL) }
                )

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedValueCells,
                    onEach = { _, _ -> delay(DELAY_FOR_EACH_CELL) }
                )

                statefulGrid.updateCellsWith(
                    updatedCells = event.revealedMineCells,
                    onEach = { _, _ -> delay(DELAY_FOR_EACH_CELL) }
                )
            }

            is MinefieldEvent.OnGameComplete -> {

                statefulGrid.updateCellsWith(
                    updatedCells = event.updatedCells,
                )
            }
        }.exhaustive
    }

    private suspend fun handleFirstClick(
        action: MinefieldAction.OnCellRevealed,
        parentGrid: StatefulGrid,
        gridGenerator: GridGenerator,
    ): MinefieldEvent {

        val grid = gridGenerator.generateGrid(
            gridSize = parentGrid.gridSize,
            starCell = action.cell.position,
            mineCount = parentGrid.totalMines,
        )

        parentGrid.updateCellsWith(
            updatedCells = grid.cells.flatten()
        )

        val clickEvent = MinefieldAction.OnCellRevealed(
            cell = grid[action.cell.position] as RawCell.UnrevealedCell
        )

        return minefieldController.onAction(
            action = clickEvent,
            mineGrid = grid,
        )
    }

    private fun updateGameState(event: MinefieldEvent) {
        val previousState = _gameState.value
        val newState = resolveGameState(event = event) ?: return
        _gameState.value = newState
        if (previousState != newState) onStateChanged(newState)
    }

    private fun updateGameProgress(statefulGrid: StatefulGrid) {
        val progress = statefulGrid.getCurrentGrid().getProgress()
        _progress.value = progress
    }

    private fun isInIdleState(): Boolean = _gameState.value == GameState.Idle

    private fun InitialGrid.getGameState(): GameState {
        return when (this) {
            is InitialGrid.NewGrid -> GameState.Idle
            is InitialGrid.InProgressGrid ->
                GameState.GameStarted
                    .nowWithNegativeOffsetOf(seconds = this.elapsedDuration)
        }
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

            is MinefieldEvent.OnCellsUpdated -> {
                if (currentState is GameState.Idle) GameState.GameStarted.now() else null
            }

            is MinefieldEvent.OnGameOver -> {
                require(currentState is GameState.StartedGameState)
                GameState.GameEnded.GameOver.now(startTime = currentState.startTime)
            }

            is MinefieldEvent.OnGameComplete -> {
                require(currentState !is GameState.Idle) {
                    "Game cannot complete without being in started state, current state : $currentState"
                }
                if (currentState !is GameState.GameStarted) null
                else GameState.GameEnded.GameCompleted.now(startTime = currentState.startTime)
            }
        }

        return state
    }

    private fun CellInteraction.mapToAction(): MinefieldAction? {

        return when (toggleState.value) {

            ToggleState.Flag -> when (this) {

                is CellInteraction.OnUnFlaggedCelClicked -> {
                    val cell = statefulGrid[position] as RawCell.UnrevealedCell
                    if (isInIdleState()) MinefieldAction.OnCellRevealed(cell = cell)
                    else MinefieldAction.OnFlagToggled(cell = cell)
                }

                is CellInteraction.OnUnFlaggedCelLongPressed,
                is CellInteraction.OnFlaggedCelLongPressed,
                -> {
                    val cell = statefulGrid[position] as RawCell.UnrevealedCell
                    MinefieldAction.OnCellRevealed(cell = cell)
                }

                is CellInteraction.OnValueCellClicked -> {
                    val rawCell = statefulGrid[position] as RawCell.RevealedCell
                    val cell = rawCell.cell as MineCell.ValuedCell.Cell
                    MinefieldAction.OnValueCellClicked(cell = cell)
                }

                is CellInteraction.OnFlaggedCelClicked -> null
            }.exhaustive

            ToggleState.Reveal -> when (this) {

                is CellInteraction.OnUnFlaggedCelClicked -> {
                    val cell = statefulGrid[position] as RawCell.UnrevealedCell
                    MinefieldAction.OnCellRevealed(cell = cell)
                }
                is CellInteraction.OnUnFlaggedCelLongPressed,
                is CellInteraction.OnFlaggedCelLongPressed,
                -> {
                    val cell = statefulGrid[position] as RawCell.UnrevealedCell
                    MinefieldAction.OnFlagToggled(cell = cell)
                }

                is CellInteraction.OnValueCellClicked -> {
                    val rawCell = statefulGrid[position] as RawCell.RevealedCell
                    val cell = rawCell.cell as MineCell.ValuedCell.Cell
                    MinefieldAction.OnValueCellClicked(cell = cell)
                }

                is CellInteraction.OnFlaggedCelClicked -> null
            }.exhaustive
        }.exhaustive
    }

    companion object {

        private const val DELAY_FOR_EACH_CELL: Long = 50L
    }
}
