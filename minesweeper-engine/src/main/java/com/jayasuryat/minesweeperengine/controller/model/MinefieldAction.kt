package com.jayasuryat.minesweeperengine.controller.model

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell

@Immutable
public sealed interface MinefieldAction {

    @Immutable
    public data class OnCellClicked(
        val cell: RawCell.UnrevealedCell,
    ) : MinefieldAction

    @Immutable
    public data class OnValueCellClicked(
        val cell: MineCell.ValuedCell,
    ) : MinefieldAction

    @Immutable
    public data class OnCellLongPressed(
        val cell: RawCell.UnrevealedCell,
    ) : MinefieldAction
}
