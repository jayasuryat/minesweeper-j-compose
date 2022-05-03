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
package com.jayasuryat.minesweeperui.cell.interaction

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperui.action.CellInteraction
import com.jayasuryat.minesweeperui.action.CellInteractionListener

@Stable
internal class CellInteractionMapper(
    private val position: Position,
    private val listener: CellInteractionListener,
) : DisplayCellInteractionListener {

    override fun onUnFlaggedCellClicked() {
        val event = CellInteraction.OnUnFlaggedCelClicked(position)
        listener.action(event)
    }

    override fun onUnFlaggedCellLongPressed() {
        val event = CellInteraction.OnUnFlaggedCelLongPressed(position)
        listener.action(event)
    }

    override fun onFlaggedCellClicked() {
        val event = CellInteraction.OnFlaggedCelClicked(position)
        listener.action(event)
    }

    override fun onFlaggedCellLongPressed() {
        val event = CellInteraction.OnFlaggedCelClicked(position)
        listener.action(event)
    }

    override fun onValueCellClicked() {
        val event = CellInteraction.OnValueCellClicked(position)
        listener.action(event)
    }
}
