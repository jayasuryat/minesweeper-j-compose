/*
 * Copyright 2021 Jaya Surya Thotapalli
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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.feedback.GameFeedback
import com.jayasuryat.uigame.composable.topbar.GameTopBar
import com.jayasuryat.uigame.feedback.MusicManager
import com.jayasuryat.uigame.feedback.VibrationManager
import com.jayasuryat.uigame.logic.ActionListener
import com.jayasuryat.uigame.logic.EmptyGridGenerator
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.util.LogCompositions
import kotlinx.coroutines.Dispatchers

@Composable
fun GameScreen(
    gameConfiguration: GameConfiguration,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

    val statefulGridState = remember {
        val statefulGrid = getStatefulGrid(gameConfiguration = gameConfiguration)
        mutableStateOf(statefulGrid)
    }

    val statefulGrid = statefulGridState.value

    val coroutineScope = rememberCoroutineScope { Dispatchers.Default }
    val layoutInfo = remember { GridLayoutInformation.from(statefulGrid = statefulGrid) }
    val context = LocalContext.current
    val musicManager = MusicManager(context)
    val vibrationManager = VibrationManager(context)
    val actionsListener = remember {
        ActionListener(
            statefulGrid = statefulGrid,
            girdGenerator = MineGridGenerator(),
            minefieldController = GameController.getDefault(),
            coroutineScope = coroutineScope,
            musicManager = musicManager,
            vibrationManager = vibrationManager,
        )
    }

    val gameState = remember { actionsListener.gameState }
    val gameProgress = remember { actionsListener.gameProgress }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        LogCompositions(name = "GameScreen\$Box")

        GameFeedback(
            gameState = gameState
        )

        MinefieldScreen(
            layoutInfo = layoutInfo,
            actionListener = actionsListener,
        )

        GameTopBar(
            gameState = gameState,
            gameProgress = gameProgress,
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = TopCenter),
            onRestartClicked = onRestartClicked,
        )
    }
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
