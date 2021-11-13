package com.jayasuryat.minesweeperjc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jayasuryat.minesweeperjc.component.AnimatingGradient
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.minesweeperui.composable.grid.Minefield

@Composable
fun MinefieldScreen(
    layoutInfo: GridLayoutInformation,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "Inside MinefieldScreen")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    ) {

        LogCompositions(name = "Inside MinefieldScreen Box")

        AnimatingGradient(
            modifier = Modifier.fillMaxSize()
        )

        Minefield(
            modifier = Modifier
                .fillMaxSize(),
            mineGrid = layoutInfo,
            actionListener = actionListener,
        )
    }
}