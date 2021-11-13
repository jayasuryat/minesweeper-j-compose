package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.gridGenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridGenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.event.NoOpActionListener

@Preview(widthDp = 1000, heightDp = 1000)
@Composable
private fun Preview() {

    val gridGenerator: GridGenerator = MineGridGenerator()

    val gridSize = GridSize(rows = 10, columns = 10)
    val start = Position(row = 1, column = 1)

    val statefulGrid = gridGenerator.generateGrid(
        gridSize = gridSize,
        starCell = start,
        mineCount = 10,
    ).asStatefulGrid()

    val mineGrid = GridLayoutInformation.from(statefulGrid)

    Minefield(
        modifier = Modifier.fillMaxSize(),
        gridInfo = mineGrid,
        actionListener = NoOpActionListener,
    )
}