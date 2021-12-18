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
package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.block.Position

@Immutable
public sealed interface MineCell {

    public val position: Position

    @Immutable
    public data class Mine(
        override val position: Position,
    ) : MineCell

    public sealed interface ValuedCell : MineCell {

        public val value: Int

        @Immutable
        public class EmptyCell(
            override val position: Position,
        ) : ValuedCell {
            override val value: Int = 0
        }

        @Immutable
        public class Cell(
            override val value: Int,
            override val position: Position,
        ) : ValuedCell
    }
}
