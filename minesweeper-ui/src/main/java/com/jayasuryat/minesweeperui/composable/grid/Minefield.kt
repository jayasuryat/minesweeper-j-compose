package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.component.ZoomableContent
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.event.NoOpActionListener

@Composable
public fun Minefield(
    modifier: Modifier,
    mineGrid: GridLayoutInformation,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "inside Minefield")

    ZoomableContent { zoomModifier: Modifier ->

        MineGrid(
            modifier = modifier.then(zoomModifier),
            mineGrid = mineGrid,
            actionListener = actionListener,
        )
    }
}