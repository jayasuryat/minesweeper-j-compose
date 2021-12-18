package com.jayasuryat.minesweeperengine.controller

import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.model.grid.Grid

public interface ActionHandler<T : MinefieldAction> {

    public suspend fun onAction(
        action: T,
        grid: Grid,
    ): MinefieldEvent
}
