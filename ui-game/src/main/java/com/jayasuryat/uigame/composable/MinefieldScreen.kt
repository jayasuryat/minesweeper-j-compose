package com.jayasuryat.uigame.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.minesweeperui.composable.grid.Minefield

@Composable
internal fun MinefieldScreen(
    modifier: Modifier = Modifier,
    layoutInfo: GridLayoutInformation,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "MinefieldScreen")

    Box(
        modifier = modifier
    ) {

        LogCompositions(name = "MinefieldScreen\$Box")

        AnimatingGradient(
            modifier = Modifier.fillMaxSize()
        )

        Minefield(
            modifier = Modifier
                .fillMaxSize(),
            gridInfo = layoutInfo,
            actionListener = actionListener,
        )
    }
}