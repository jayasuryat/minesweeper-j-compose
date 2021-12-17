package com.jayasuryat.uigame.logic

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

internal class EmptyGridGenerator {

    fun generateEmptyGrid(
        gridSize: GridSize,
        mineCount: Int,
    ): Grid = generateFor(
        gridSize = gridSize,
        mineCount = mineCount,
    )

    private fun generateFor(
        gridSize: GridSize,
        mineCount: Int,
    ): Grid {

        val rows = gridSize.rows
        val columns = gridSize.columns

        return EmptyGrid(
            gridSize = gridSize,
            totalMines = mineCount,
            cells = (0 until rows).map { row ->
                (0 until columns).map { column ->
                    val position = Position(row = row, column = column)
                    val cell = MineCell.ValuedCell.EmptyCell(position = position)
                    RawCell.UnrevealedCell.UnFlaggedCell(cell = cell)
                }
            },
        )
    }

    @Stable
    private data class EmptyGrid(
        override val gridSize: GridSize,
        override val totalMines: Int,
        override val cells: List<List<RawCell>>,
    ) : Grid {

        override operator fun get(position: Position): RawCell {
            return cells[position.row][position.column]
        }

        override fun getOrNull(position: Position): RawCell? {
            return kotlin.runCatching { get(position) }.getOrNull()
        }
    }
}
