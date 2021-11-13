package com.jayasuryat.minesweeperui.composable.event

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.MinefieldAction

@Stable
public fun interface MinefieldActionsListener {

    public fun action(action: MinefieldAction)
}