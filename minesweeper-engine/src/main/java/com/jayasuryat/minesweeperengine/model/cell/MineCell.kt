package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.block.Position

@Stable
public sealed class MineCell {

    public abstract val position: Position

    @Stable
    public data class Mine(
        override val position: Position,
    ) : MineCell()

    @Stable
    public class EmptyCell(
        override val position: Position,
    ) : MineCell()

    @Stable
    public class Cell(
        public val value: Int,
        override val position: Position,
    ) : MineCell()
}
