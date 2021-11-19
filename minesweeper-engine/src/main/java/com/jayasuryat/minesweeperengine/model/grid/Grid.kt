package com.jayasuryat.minesweeperengine.model.grid

import androidx.compose.runtime.StableMarker
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@StableMarker
public interface Grid {

    public val gridSize: GridSize

    public val totalMines: Int

    public val cells: List<List<RawCell>>

    public operator fun get(position: Position): RawCell

    public fun getOrNull(position: Position): RawCell?
}