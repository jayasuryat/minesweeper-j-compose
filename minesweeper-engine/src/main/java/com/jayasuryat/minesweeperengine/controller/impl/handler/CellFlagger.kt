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
package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.controller.ActionHandler
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.util.exhaustive

internal class CellFlagger : ActionHandler<MinefieldAction.OnFlagToggled> {

    override suspend fun onAction(
        action: MinefieldAction.OnFlagToggled,
        grid: Grid,
    ): MinefieldEvent {

        val updatedCell = when (action.cell) {

            is RawCell.UnrevealedCell.FlaggedCell -> action.cell.asUnFlagged()

            is RawCell.UnrevealedCell.UnFlaggedCell -> action.cell.asFlagged()
        }.exhaustive

        return MinefieldEvent.OnCellsUpdated(updatedCells = listOf(updatedCell))
    }
}
