package com.jayasuryat.minesweeperjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.MinefieldControllerV1
import com.jayasuryat.minesweeperengine.controller.MinefieldEvent
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperjc.component.AnimatingGradient
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.minesweeperui.composable.grid.Minefield
import com.jayasuryat.minesweeperengine.model.grid.Grid as MineGridData

class MainActivity : ComponentActivity() {

    private val minefieldController: MinefieldController by lazy { MinefieldControllerV1() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MinesweeperJCTheme {
                // A surface container using the 'background' color from the theme

                val mineGrid = remember { mutableStateOf(getMineGrid()) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Cyan)
                ) {

                    AnimatingGradient(
                        modifier = Modifier.fillMaxSize()
                    )

                    Minefield(
                        modifier = Modifier
                            .fillMaxSize(),
                        mineGrid = mineGrid.value,

                        actionListener = { action ->

                            val state = minefieldController.onAction(
                                action = action,
                                mineGrid = mineGrid.value,
                            )
                            when (state) {
                                is MinefieldEvent.OnGridUpdated -> {
                                    mineGrid.value = state.mineGrid
                                }
                                is MinefieldEvent.OnGameOver -> Unit
                            }
                        },
                    )
                }
            }
        }
    }

    private fun getMineGrid(): MineGridData {

        val gridGenerator: GridGenerator = MineGridGenerator()
        //val gridGenerator: GridGenerator = DebugMineGridGenerator()
        //val gridGenerator: GridGenerator = RevealedMineGridGenerator(MineGridGenerator())

        val gridSize = GridSize(
            rows = 10,
            columns = 10,
        )
        val start = Position(row = 1, column = 1)

        return gridGenerator.generateGrid(
            gridSize = gridSize,
            starCell = start,
            mineCount = 10,
        )
    }
}