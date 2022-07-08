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
package com.jayasuryat.minesweeperui.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@Stable
public interface DisplayCell {

    public val position: Position
    public val cellState: State<Cell>

    @Immutable
    public sealed interface Cell {

        @Immutable
        public object UnFlaggedCell : Cell

        @Immutable
        public object FlaggedCell : Cell

        @Immutable
        public object Mine : Cell

        @Immutable
        public object EmptyCell : Cell

        @Immutable
        public data class ValueCell(
            val value: Int,
        ) : Cell

        public companion object {

            public fun from(source: RawCell): Cell {

                return when (source) {
                    is RawCell.UnrevealedCell.UnFlaggedCell -> UnFlaggedCell
                    is RawCell.UnrevealedCell.FlaggedCell -> FlaggedCell
                    is RawCell.RevealedCell -> when (val mineCell = source.cell) {
                        is MineCell.Mine -> Mine
                        is MineCell.ValuedCell.EmptyCell -> EmptyCell
                        is MineCell.ValuedCell.Cell -> ValueCell(
                            value = mineCell.value
                        )
                    }
                }
            }
        }
    }
}
