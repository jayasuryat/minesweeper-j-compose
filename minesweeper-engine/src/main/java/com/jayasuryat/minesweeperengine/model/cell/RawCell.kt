package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.block.Position

@Immutable
public sealed class RawCell {

    public abstract val position: Position

    @Immutable
    public class UnrevealedCell(
        private val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position

        public fun asRevealed(): RevealedCell = RevealedCell(cell)
    }

    @Immutable
    public class RevealedCell(
        public val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position
    }
}
