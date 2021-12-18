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
package com.jayasuryat.minesweeperengine.controller.model

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@Immutable
public sealed interface MinefieldEvent {

    @Immutable
    public data class OnCellsUpdated(
        val updatedCells: List<RawCell>,
    ) : MinefieldEvent

    @Immutable
    public data class OnGameOver(
        val revealedMineCells: List<RawCell.RevealedCell>,
        val revealedValueCells: List<RawCell.RevealedCell>,
        val revealedEmptyCells: List<RawCell.RevealedCell>,
    ) : MinefieldEvent

    @Immutable
    public data class OnGameComplete(
        val updatedCells: List<RawCell>,
    ) : MinefieldEvent
}
