package com.jayasuryat.minesweeperengine.controller

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.grid.Grid

@Stable
public interface MinefieldController {

    public suspend fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent
}
