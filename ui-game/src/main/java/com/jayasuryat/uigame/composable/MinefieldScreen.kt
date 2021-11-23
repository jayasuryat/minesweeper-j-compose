package com.jayasuryat.uigame.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.minesweeperui.composable.grid.Minefield
import com.jayasuryat.minesweeperui.composable.theme.MinesweeperColors
import com.jayasuryat.minesweeperui.composable.theme.MinesweeperTheme
import com.jayasuryat.util.LogCompositions

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

        MinesweeperTheme(
            colors = getMinefieldColors(),
        ) {

            Minefield(
                modifier = Modifier
                    .fillMaxSize(),
                gridInfo = layoutInfo,
                actionListener = actionListener,
            )
        }
    }
}

@Composable
@ReadOnlyComposable
private fun getMinefieldColors(): MinesweeperColors {

    val colors = if (isSystemInDarkTheme()) {

        // Dark colors
        MinesweeperColors(
            minefield = Color.Black,
            text = Color.White,
            mine = Color.Red,
            flagIconTint = Color.Black,
            mineIconTint = Color.White,
        )
    } else {

        // Light colors
        MinesweeperColors(
            minefield = Color(0xFFA9D2A4),
            text = Color(0xFFA9D2A4),
            mine = Color.Red,
            flagIconTint = Color.White,
            mineIconTint = Color.White,
        )
    }

    return colors
}