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
package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.gridgenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.state.asStatefulGrid
import com.jayasuryat.minesweeperui.composable.action.NoOpActionListener
import kotlinx.coroutines.runBlocking

@Preview(widthDp = 1000, heightDp = 1000)
@Composable
private fun Preview() {

    val gridGenerator: GridGenerator = MineGridGenerator()

    val gridSize = GridSize(rows = 10, columns = 10)
    val start = Position(row = 1, column = 1)

    val statefulGrid = runBlocking {
        gridGenerator.generateGrid(
            gridSize = gridSize,
            starCell = start,
            mineCount = 10,
        ).asStatefulGrid()
    }

    val mineGrid = GridLayoutInformation.from(statefulGrid)

    Minefield(
        modifier = Modifier.fillMaxSize(),
        gridInfo = mineGrid,
        actionListener = NoOpActionListener,
    )
}
