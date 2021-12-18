/*
 * Copyright 2021 Jaya Surya Thotapalli
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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@Stable
public interface StatefulGrid {

    public val gridSize: GridSize

    public val totalMines: Int

    public val cells: List<List<State<RawCell>>>

    public operator fun get(position: Position): State<RawCell>

    public fun getOrNull(position: Position): State<RawCell>?

    public fun updateCellsWith(
        updatedCells: List<RawCell>,
    )

    public suspend fun updateCellsWith(
        updatedCells: List<RawCell>,
        onEach: suspend (oldCell: RawCell, newCell: RawCell) -> Unit,
    )
}
