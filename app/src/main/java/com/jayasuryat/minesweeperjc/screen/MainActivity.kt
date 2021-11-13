package com.jayasuryat.minesweeperjc.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperengine.state.getCurrentGrid
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MinesweeperJCTheme {
                // A surface container using the 'background' color from the theme
                LogCompositions(name = "Inside MinesweeperJCTheme")

                val params = getMinefieldParams()

                MinefieldScreen(
                    layoutInfo = params.layoutInformation,
                    actionListener = params.actionListener,
                )
            }
        }
    }
}

@Stable
private fun getMinefieldParams(): MinefieldScreenParams {

    val gridGenerator: GridGenerator = MineGridGenerator()
    //val gridGenerator: GridGenerator = DebugMineGridGenerator()
    //val gridGenerator: GridGenerator = RevealedMineGridGenerator(MineGridGenerator())

    val gridSize = GridSize(
        rows = 10,
        columns = 10,
    )
    val start = Position(row = 1, column = 1)

    val grid = gridGenerator.generateGrid(
        gridSize = gridSize,
        starCell = start,
        mineCount = 10,
    )

    val statefulGrid = grid.asStatefulGrid()
    val layoutInfo = GridLayoutInformation.from(statefulGrid)
    val controller = GameController.getDefault()

    return MinefieldScreenParams(
        layoutInformation = layoutInfo,
        actionListener = Test(
            mineGrid = statefulGrid,
            minefieldController = controller,
        ),
    )
}

@Stable
private class Test(
    private val mineGrid: StatefulGrid,
    private val minefieldController: MinefieldController,
) : MinefieldActionsListener {
    override fun action(action: MinefieldAction) {
        val state = minefieldController.onAction(
            action = action,
            mineGrid = mineGrid.getCurrentGrid(),
        )
        when (state) {
            is MinefieldEvent.OnGridUpdated -> {
                mineGrid.updateStatesWith(state.mineGrid.cells)
            }
            is MinefieldEvent.OnGameOver -> Unit
        }
    }
}

@Stable
private data class MinefieldScreenParams(
    val layoutInformation: GridLayoutInformation,
    val actionListener: MinefieldActionsListener,
)