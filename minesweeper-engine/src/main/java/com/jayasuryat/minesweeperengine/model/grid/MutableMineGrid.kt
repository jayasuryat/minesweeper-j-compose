package com.jayasuryat.minesweeperengine.model.grid

import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell

internal data class MutableMineGrid(
    override val gridSize: GridSize,
    private val mutableCells: MutableList<MutableList<RawCell>> = mutableListOf(),
) : Grid {

    override val cells: List<List<RawCell>> = mutableCells

    override operator fun get(position: Position): RawCell {
        return cells[position.row][position.column]
    }

    operator fun set(position: Position, value: RawCell) {
        mutableCells[position.row][position.column] = value
    }

    companion object {

        private fun List<List<RawCell>>.validateCellsAndGetSize(): GridSize {

            val cells = this

            require(cells.isNotEmpty()) { "Cannot create mine grid from empty cells list" }

            val rows = cells.size
            val columns = cells.first().size

            val hasConsistentColumns = cells.all { row -> row.size == columns }

            require(hasConsistentColumns) { "Cells with inconsistent column size" }

            return GridSize(
                rows = rows,
                columns = columns,
            )
        }

        fun from(cells: List<List<RawCell>>): MutableMineGrid {

            val gridSize = cells.validateCellsAndGetSize()

            return MutableMineGrid(
                gridSize = gridSize,
                mutableCells = cells.map { it.toMutableList() }.toMutableList(),
            )
        }
    }
}