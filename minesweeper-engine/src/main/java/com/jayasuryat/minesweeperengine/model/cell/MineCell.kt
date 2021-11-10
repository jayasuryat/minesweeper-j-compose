package com.jayasuryat.minesweeperengine.model.cell

import com.jayasuryat.minesweeperengine.model.block.Position


public sealed class MineCell {

    public abstract val position: Position

    public data class Mine(
        override val position: Position,
    ) : MineCell()

    public class EmptyCell(
        override val position: Position,
    ) : MineCell()

    public class Cell(
        public val value: Int,
        override val position: Position,
    ) : MineCell()
}
