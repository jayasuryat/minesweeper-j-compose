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
package com.jayasuryat.uigame

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.uigame.feedback.MusicManager
import com.jayasuryat.uigame.feedback.VibrationManager
import com.jayasuryat.uigame.logic.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GameViewModel(
    context: Context,
    gameConfiguration: GameConfiguration,
) : ViewModel() {

    internal val statefulGrid: StatefulGrid = getStatefulGrid(
        gameConfiguration = gameConfiguration,
    )

    private val toggleState: MutableState<ToggleState> = mutableStateOf(ToggleState.Reveal)

    private val _actionListener: ActionListener = ActionListener(
        statefulGrid = statefulGrid,
        girdGenerator = MineGridGenerator(),
        minefieldController = GameController.getDefault(),
        toggleState = toggleState,
        coroutineScope = CoroutineScope(Dispatchers.Default),
        musicManager = MusicManager(context),
        vibrationManager = VibrationManager(context),
    )
    internal val actionLister: CellInteractionListener = _actionListener

    internal val gameState: State<GameState> = _actionListener.gameState
    internal val gameProgress: State<GameProgress> = _actionListener.gameProgress

    internal fun onToggleStateUpdated(newState: ToggleState) {
        toggleState.value = newState
    }

    @Stable
    private fun getStatefulGrid(
        gameConfiguration: GameConfiguration,
    ): StatefulGrid {

        val gridGenerator = EmptyGridGenerator()

        val gridSize = GridSize(
            rows = gameConfiguration.rows,
            columns = gameConfiguration.columns,
        )

        val grid = gridGenerator.generateEmptyGrid(
            gridSize = gridSize,
            mineCount = gameConfiguration.mines,
        )

        return grid.asStatefulGrid()
    }
}
