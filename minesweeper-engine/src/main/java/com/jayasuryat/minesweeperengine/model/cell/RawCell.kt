package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.block.Position

@Immutable
public sealed interface RawCell {

    public val position: Position

    @Immutable
    public sealed interface UnrevealedCell : RawCell {

        public fun asRevealed(): RevealedCell

        @Immutable
        public class UnFlaggedCell(
            private val cell: MineCell,
        ) : UnrevealedCell {

            override val position: Position = cell.position

            override fun asRevealed(): RevealedCell = RevealedCell(cell = cell)

            internal fun asFlagged(): FlaggedCell = FlaggedCell(cell = cell)
        }

        @Immutable
        public class FlaggedCell(
            private val cell: MineCell,
        ) : UnrevealedCell {

            override val position: Position = cell.position

            override fun asRevealed(): RevealedCell = RevealedCell(cell = cell)

            public fun asUnFlagged(): UnFlaggedCell = UnFlaggedCell(cell = cell)
        }
    }

    @Immutable
    public class RevealedCell(
        public val cell: MineCell,
    ) : RawCell {

        override val position: Position = cell.position
    }
}
