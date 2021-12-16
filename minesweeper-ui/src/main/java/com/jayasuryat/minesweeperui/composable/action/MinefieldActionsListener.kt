package com.jayasuryat.minesweeperui.composable.action

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction

@Stable
public fun interface MinefieldActionsListener {

    public fun action(action: MinefieldAction)
}