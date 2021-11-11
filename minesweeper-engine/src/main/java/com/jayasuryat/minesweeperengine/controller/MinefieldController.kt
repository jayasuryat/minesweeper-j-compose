package com.jayasuryat.minesweeperengine.controller

import com.jayasuryat.minesweeperengine.model.grid.Grid

public interface MinefieldController {

    public fun onAction(
        action: MinefieldAction,
        mineGrid: Grid,
    ): MinefieldEvent
}