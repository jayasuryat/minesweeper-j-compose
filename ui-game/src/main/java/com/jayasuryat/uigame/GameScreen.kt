package com.jayasuryat.uigame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.feedback.GameFeedback
import com.jayasuryat.uigame.composable.topbar.GameTopBar
import com.jayasuryat.uigame.feedback.MusicManager
import com.jayasuryat.uigame.feedback.VibrationManager
import com.jayasuryat.uigame.logic.ActionListener
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.util.LogCompositions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GameScreen(
    gameConfiguration: GameConfiguration,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

    val statefulGridState = remember { mutableStateOf<StatefulGrid?>(null) }

    LaunchedEffect(key1 = gameConfiguration.gameId) {
        withContext(Dispatchers.Default) {
            statefulGridState.value = getStatefulGrid(gameConfiguration)
        }
    }

    // TODO: 25/11/21 : Handle Loading state
    val statefulGrid = statefulGridState.value ?: return

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
private suspend fun getStatefulGrid(
    gameConfiguration: GameConfiguration,
): StatefulGrid {

    val gridGenerator: GridGenerator = MineGridGenerator()
    //val gridGenerator: GridGenerator = DebugMineGridGenerator()
    //val gridGenerator: GridGenerator = RevealedMineGridGenerator(MineGridGenerator())

    val gridSize = GridSize(
        rows = gameConfiguration.rows,
        columns = gameConfiguration.columns,
    )

    // TODO: 14/11/21 : Need to drive this from the first clicked mine
    val start = Position(row = 1, column = 1)

    val grid = gridGenerator.generateGrid(
        gridSize = gridSize,
        starCell = start,
        mineCount = gameConfiguration.mines,
    )

    return grid.asStatefulGrid()
}