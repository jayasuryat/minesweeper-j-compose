package com.jayasuryat.minesweeperui.composable.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
public class MinesweeperColors(
    public val minefield: Color,
    public val text: Color,
    public val mine: Color,
    public val flagIconTint: Color,
    public val mineIconTint: Color,
)

internal val DefaultColors: MinesweeperColors = MinesweeperColors(
    minefield = Color.Black,
    text = Color.White,
    mine = Color.Red,
    flagIconTint = Color.Black,
    mineIconTint = Color.White,
)