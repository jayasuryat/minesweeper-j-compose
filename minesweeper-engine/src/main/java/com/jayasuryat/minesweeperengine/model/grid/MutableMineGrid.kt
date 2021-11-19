package com.jayasuryat.minesweeperengine.model.grid

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell

internal data class MutableMineGrid(
    override val gridSize: GridSize,
    override val totalMines: Int,
    private val mutableCells: MutableList<MutableList<RawCell>> = mutableListOf(),
) : Grid {

    override val cells: List<List<RawCell>> = mutableCells

    override operator fun get(position: Position): RawCell {
        return cells[position.row][position.column]
    }

    override fun getOrNull(position: Position): RawCell? {
        return kotlin.runCatching { get(position) }.getOrNull()
    }

    operator fun <T : RawCell> set(position: Position, value: T) {
        mutableCells[position.row][position.column] = value
    }

    companion object {

        fun from(grid: Grid): MutableMineGrid {

            return MutableMineGrid(
                gridSize = grid.gridSize,
                totalMines = grid.totalMines,
                mutableCells = grid.cells.map { it.toMutableList() }.toMutableList(),
            )
        }
    }
}