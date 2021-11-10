package com.jayasuryat.minesweeperjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperenginedebug.generator.RevealedMineGridGenerator
import com.jayasuryat.minesweeperjc.ui.theme.MinesweeperJCTheme
import com.jayasuryat.minesweeperui.composable.grid.Minefield
import com.jayasuryat.minesweeperengine.model.grid.MineGrid as MineGridData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinesweeperJCTheme {
                // A surface container using the 'background' color from the theme

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ) {

                    Minefield(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f),
                        mineGrid = getMineGrid(),
                    )
                }
            }
        }
    }

    private fun getMineGrid(): MineGridData {

        //val gridGenerator: GridGenerator = DebugMineGridGenerator()
        val gridGenerator: GridGenerator = RevealedMineGridGenerator(MineGridGenerator())

        val gridSize = GridSize(rows = 20, columns = 20)
        val start = Position(row = 1, column = 1)

        return gridGenerator.generateGrid(
            gridSize = gridSize,
            starCell = start,
            mineCount = 40,
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MinesweeperJCTheme {
        Greeting("Android")
    }
}