package com.jayasuryat.uigame

import android.content.Context
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.action.MinefieldActionsListener
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

    private val _actionListener: ActionListener = ActionListener(
        statefulGrid = statefulGrid,
        girdGenerator = MineGridGenerator(),
        minefieldController = GameController.getDefault(),
        coroutineScope = CoroutineScope(Dispatchers.Default),
        musicManager = MusicManager(context),
        vibrationManager = VibrationManager(context),
    )
    internal val actionLister: MinefieldActionsListener = _actionListener

    internal val gameState: State<GameState> = _actionListener.gameState
    internal val gameProgress: State<GameProgress> = _actionListener.gameProgress

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