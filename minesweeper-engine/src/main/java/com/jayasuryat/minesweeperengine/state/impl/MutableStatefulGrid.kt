package com.jayasuryat.minesweeperengine.state.impl

import androidx.compose.runtime.*
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.state.StatefulGrid

@Stable
internal class MutableStatefulGrid(
    override val gridSize: GridSize,
    private val mutableCells: List<List<MutableState<RawCell>>> = getEmptyCellsList(gridSize),
) : StatefulGrid {

    override val cells: List<List<State<RawCell>>> = mutableCells

    private fun getMutableCell(position: Position): MutableState<RawCell> {
        return mutableCells[position.row][position.column]
    }

    override fun get(position: Position): State<RawCell> = getMutableCell(position)

    override fun getOrNull(position: Position): State<RawCell>? {
        return kotlin.runCatching { mutableCells[position.row][position.column] }.getOrNull()
    }

    override fun updateCellsWith(updatedCells: List<RawCell>) {
        updatedCells.forEach { cell ->
            getMutableCell(cell.position).value = cell
        }
    }

    override fun updateCellsWith(
        updatedCells: List<RawCell>,
        delayForEachCell: Long,
    ) {
        updatedCells.forEach { cell ->
            getMutableCell(cell.position).value = cell
            //   delay(delayForEachCell)
        }
    }

    internal companion object {

        private fun emptyCell(
            row: Int,
            column: Int,
        ): MineCell.ValuedCell.EmptyCell {
            val position = Position(row = row, column = column)
            return MineCell.ValuedCell.EmptyCell(position = position)
        }

        private fun getEmptyCellsList(
            gridSize: GridSize,
        ): List<List<MutableState<RawCell>>> {

            val rows = gridSize.rows
            val columns = gridSize.columns

            val cells: List<List<MutableState<RawCell>>> = (0 until rows).map { row ->
                (0 until columns).map { column ->
                    val cell = emptyCell(row = row, column = column)
                    val rawCell = RawCell.UnrevealedCell.UnFlaggedCell(cell)
                    mutableStateOf(
                        value = rawCell,
                        policy = structuralEqualityPolicy(),
                    )
                }
            }
            return cells
        }

        internal fun from(grid: Grid): MutableStatefulGrid {

            val cells: List<List<MutableState<RawCell>>> = grid.cells.map { row ->
                row.map { rawCell ->
                    mutableStateOf(
                        value = rawCell,
                        policy = structuralEqualityPolicy(),
                    )
                }
            }

            return MutableStatefulGrid(
                gridSize = grid.gridSize,
                mutableCells = cells,
            )
        }
    }
}