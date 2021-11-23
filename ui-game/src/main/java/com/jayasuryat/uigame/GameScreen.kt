package com.jayasuryat.uigame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.timer.GameTopBar
import com.jayasuryat.util.LogCompositions
import kotlinx.coroutines.Dispatchers

@Composable
fun GameScreen(
    gameConfiguration: GameConfiguration,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

    val statefulGrid = getStatefulGrid(gameConfiguration = gameConfiguration)

    val coroutineScope = rememberCoroutineScope { Dispatchers.Default }
    val layoutInfo = remember { GridLayoutInformation.from(statefulGrid = statefulGrid) }
    val actionsListener = remember {
        ActionListener(
            statefulGrid = statefulGrid,
            minefieldController = GameController.getDefault(),
            coroutineScope = coroutineScope,
        )
    }
    val gameState = remember { actionsListener.gameState }

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