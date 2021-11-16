package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperui.composable.component.ZoomableContent
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.util.LogCompositions

@Composable
public fun Minefield(
    modifier: Modifier,
    gridInfo: GridLayoutInformation,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "Minefield")

    ZoomableContent { zoomModifier: Modifier ->

        MineGrid(
            modifier = modifier.then(zoomModifier),
            gridInfo = gridInfo,
            actionListener = actionListener,
        )
    }
}