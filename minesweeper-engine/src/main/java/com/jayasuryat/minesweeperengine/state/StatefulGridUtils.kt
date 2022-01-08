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
package com.jayasuryat.minesweeperengine.state

import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.model.grid.MineGrid
import com.jayasuryat.minesweeperengine.state.impl.MutableStatefulGrid

public fun Grid.asStatefulGrid(): StatefulGrid {
    return MutableStatefulGrid.from(this)
}

public fun StatefulGrid.getCurrentGrid(): Grid {

    return MineGrid(
        gridSize = this.gridSize,
        totalMines = this.totalMines,
        cells = this.cells.map { row ->
            row.map { cell ->
                cell.value
            }
        }
    )
}
