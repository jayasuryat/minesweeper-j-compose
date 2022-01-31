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
package com.jayasuryat.minesweeperui.action

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@Immutable
public sealed interface CellInteraction {

    @Immutable
    public data class OnCellClicked(
        val cell: RawCell.UnrevealedCell,
    ) : CellInteraction

    @Immutable
    public data class OnValueCellClicked(
        val cell: MineCell.ValuedCell.Cell,
    ) : CellInteraction

    @Immutable
    public data class OnCellLongPressed(
        val cell: RawCell.UnrevealedCell,
    ) : CellInteraction
}