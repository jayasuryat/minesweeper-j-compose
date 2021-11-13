package com.jayasuryat.minesweeperengine.controller

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Stable
public interface MinefieldController {

    public fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent
}