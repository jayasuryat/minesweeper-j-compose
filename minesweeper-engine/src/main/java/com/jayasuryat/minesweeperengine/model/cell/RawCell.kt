package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.block.Position

@Stable
public sealed class RawCell {

    public abstract val position: Position

    @Stable
    public class UnrevealedCell(
        private val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position

        public fun asRevealed(): RevealedCell = RevealedCell(cell)
    }

    @Stable
    public class RevealedCell(
        public val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position
    }
}
