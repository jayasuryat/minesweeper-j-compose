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
