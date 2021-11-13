package com.jayasuryat.minesweeperengine.controller.model

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Immutable
public sealed interface MinefieldEvent {

    @Immutable
    public data class OnGridUpdated(
        val mineGrid: Grid,
    ) : MinefieldEvent

    @Immutable
    public object OnGameOver : MinefieldEvent
}
