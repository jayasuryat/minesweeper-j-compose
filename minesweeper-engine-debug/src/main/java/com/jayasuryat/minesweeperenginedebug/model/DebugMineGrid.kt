package com.jayasuryat.minesweeperenginedebug.model

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Immutable
internal data class DebugMineGrid(
    override val gridSize: GridSize,
    private val mutableCells: MutableList<MutableList<RawCell>>,
) : Grid {

    override val cells: List<List<RawCell>> = mutableCells

    override operator fun get(position: Position): RawCell {
        return cells[position.row][position.column]
    }

    override fun getOrNull(position: Position): RawCell? {
        return kotlin.runCatching { get(position) }.getOrNull()
    }

    operator fun set(position: Position, value: RawCell) {
        mutableCells[position.row][position.column] = value
    }
}