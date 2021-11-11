package com.jayasuryat.minesweeperengine.controller

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Immutable
public sealed class MinefieldEvent {

    public data class OnGridUpdated(
        val mineGrid: Grid,
    ) : MinefieldEvent()

    public object OnGameOver : MinefieldEvent()
}
