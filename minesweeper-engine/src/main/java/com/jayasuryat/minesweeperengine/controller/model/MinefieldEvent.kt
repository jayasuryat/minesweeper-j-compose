package com.jayasuryat.minesweeperengine.controller.model

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Immutable
public sealed interface MinefieldEvent {

    @Immutable
    public data class OnGridUpdated(
        val mineGrid: Grid,
    ) : MinefieldEvent

    @Immutable
    public data class OnCellsUpdated(
        val updatedCells: List<RawCell>,
    ) : MinefieldEvent

    @Immutable
    public data class OnGameOver(
        val revealedMineCells: List<RawCell.RevealedCell>,
        val revealedValueCells: List<RawCell.RevealedCell>,
        val revealedEmptyCells: List<RawCell.RevealedCell>,
    ) : MinefieldEvent

    @Immutable
    public data class OnGameComplete(
        val updatedCells: List<RawCell>,
    ) : MinefieldEvent
}
