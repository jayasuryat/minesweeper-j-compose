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
import com.jayasuryat.minesweeperengine.model.block.Position

@Immutable
public sealed interface CellInteraction {

    public val position: Position

    @Immutable
    public data class OnUnFlaggedCelClicked(
        override val position: Position,
    ) : CellInteraction

    @Immutable
    public data class OnUnFlaggedCelLongPressed(
        override val position: Position,
    ) : CellInteraction

    @Immutable
    public data class OnFlaggedCelClicked(
        override val position: Position,
    ) : CellInteraction

    @Immutable
    public data class OnFlaggedCelLongPressed(
        override val position: Position,
    ) : CellInteraction

    @Immutable
    public data class OnValueCellClicked(
        override val position: Position,
    ) : CellInteraction
}
