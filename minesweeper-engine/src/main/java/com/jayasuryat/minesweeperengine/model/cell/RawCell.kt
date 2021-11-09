package com.jayasuryat.minesweeperengine.model.cell

internal sealed class RawCell {

    class UnrevealedCell(
        private val cell: MineCell,
    ) : RawCell() {

        fun asRevealed(): RevealedCell = RevealedCell(cell)
    }

    class RevealedCell(
        val cell: MineCell,
    ) : RawCell()
}
