package com.jayasuryat.minesweeperengine.model.cell

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.block.Position

@Immutable
public sealed class MineCell {

    public abstract val position: Position

    @Immutable
    public data class Mine(
        override val position: Position,
    ) : MineCell()

    @Immutable
    public class EmptyCell(
        override val position: Position,
    ) : MineCell()

    @Immutable
    public class Cell(
        public val value: Int,
        override val position: Position,
    ) : MineCell()
}
