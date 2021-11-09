package com.jayasuryat.minesweeperengine.model.cell

import com.jayasuryat.minesweeperengine.model.block.Position

internal sealed class MineCell {

    abstract val position: Position

    internal data class Mine(
        override val position: Position,
    ) : MineCell()

    data class EmptyCell(
        override val position: Position,
    ) : MineCell()

    data class Cell(
        val value: Int,
        override val position: Position,
    ) : MineCell()
}
