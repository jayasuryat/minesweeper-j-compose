package com.jayasuryat.uigame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen

@Composable
fun GameScreen(
    gameConfiguration: GameConfiguration,
) {

    LogCompositions(name = "GameScreen")

    val statefulGrid = remember { getStatefulGrid(gameConfiguration = gameConfiguration) }
    val layoutInfo = remember { GridLayoutInformation.from(statefulGrid = statefulGrid) }
    val gameController = remember { GameController.getDefault() }
    val actionsListener = remember {
        ActionListener(
            statefulGrid = statefulGrid,
            minefieldController = gameController,
        )
    }

    MinefieldScreen(
        layoutInfo = layoutInfo,
        actionListener = actionsListener,
    )
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