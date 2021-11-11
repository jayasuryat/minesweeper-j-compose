package com.jayasuryat.minesweeperui.composable.event

import androidx.compose.runtime.Immutable
import com.jayasuryat.minesweeperengine.controller.MinefieldAction

@Immutable
public fun interface MinefieldActionsListener {

    public fun action(event: MinefieldAction)
}