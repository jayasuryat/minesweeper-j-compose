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
package com.jayasuryat.minesweeperui.grid

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.state.StatefulGrid

@Stable
public data class GridLayoutInformation(
    val gridSize: GridSize,
    val cells: List<CellInfo>,
) {

    @Stable
    public data class CellInfo(
        val cellState: State<RawCell>,
        val position: Position,
    )

    public companion object {

        @Stable
        public fun from(statefulGrid: StatefulGrid): GridLayoutInformation {

            return GridLayoutInformation(
                gridSize = statefulGrid.gridSize,
                cells = statefulGrid.cells.flatten().map { cell ->
                    CellInfo(cellState = cell, position = cell.value.position)
                }
            )
        }
    }
}
