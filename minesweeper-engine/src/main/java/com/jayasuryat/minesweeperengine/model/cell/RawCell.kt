package com.jayasuryat.minesweeperengine.model.cell

import com.jayasuryat.minesweeperengine.model.block.Position


public sealed class RawCell {

    public abstract val position: Position

    public class UnrevealedCell(
        private val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position

        public fun asRevealed(): RevealedCell = RevealedCell(cell)
    }

    public class RevealedCell(
        public val cell: MineCell,
    ) : RawCell() {

        override val position: Position = cell.position
    }
}
