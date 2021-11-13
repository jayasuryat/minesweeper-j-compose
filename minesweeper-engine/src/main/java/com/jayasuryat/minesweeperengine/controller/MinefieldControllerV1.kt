package com.jayasuryat.minesweeperengine.controller

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.util.exhaustive
import com.jayasuryat.minesweeperengine.util.mutate

@Stable
public class MinefieldControllerV1 : MinefieldController {

    private val cellReveler: CellReveler by lazy { CellReveler() }
    private val cellFlagger: CellFlagger by lazy { CellFlagger() }
    private val neighbourCalculator: NeighbourCalculator by lazy { NeighbourCalculator() }

    override fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent {

        return when (action) {

            is MinefieldAction.OnCellClicked -> cellReveler.reveal(
                action = action,
                mineGrid = mineGrid,
            )

            is MinefieldAction.OnValueCellClicked -> TODO()

            is MinefieldAction.OnCellLongPressed -> cellFlagger.toggleFlag(
                action = action,
                mineGrid = mineGrid,
            )

        }.exhaustive
    }

    private inner class CellReveler {

        fun reveal(
            action: MinefieldAction.OnCellClicked,
            mineGrid: Grid,
        ): MinefieldEvent {

            val revealed = action.cell.asRevealed()

            return when (val cell = revealed.cell) {

                is MineCell.ValuedCell.Cell -> {
                    val updatedGrid = mineGrid.mutate { this[action.cell.position] = revealed }
                    MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
                }

                is MineCell.ValuedCell.EmptyCell -> {
                    val updatedGrid = cell.revealNeighbouringCells(grid = mineGrid)
                    MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
                }

                is MineCell.Mine -> MinefieldEvent.OnGameOver

            }.exhaustive
        }

        private fun MineCell.ValuedCell.EmptyCell.revealNeighbouringCells(grid: Grid): Grid {

            val cells = neighbourCalculator.getAllNeighbouringValueCell(cell = this, grid = grid)

            return grid.mutate {
                cells.forEach { cell -> this[cell.position] = cell }
            }
        }
    }

    private inner class ValueCellReveler {

        fun reveal(
            cell: MineCell.ValuedCell,
            grid: Grid,
        ): Grid {

            TODO()
        }
    }

    private class CellFlagger {

        fun toggleFlag(
            action: MinefieldAction.OnCellLongPressed,
            mineGrid: Grid,
        ): MinefieldEvent {

            val updatedGrid = when (action.cell) {

                is RawCell.UnrevealedCell.FlaggedCell -> mineGrid.mutate {
                    this[action.cell.position] = action.cell.asUnFlagged()
                }

                is RawCell.UnrevealedCell.UnFlaggedCell -> mineGrid.mutate {
                    this[action.cell.position] = action.cell.asFlagged()
                }

            }.exhaustive

            return MinefieldEvent.OnGridUpdated(mineGrid = updatedGrid)
        }
    }
}