package com.jayasuryat.minesweeperjc.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = Vampire,
    onBackground = Beau,

    primary = Cobalt,
    onPrimary = Beau,

    secondary = Havelock,
    onSecondary = Beau,

    error = Ruby,
    onError = Beau,
)

private val LightColorPalette = lightColors(
    background = Navajo,
    onBackground = Beech,

    primary = Damask,
    onPrimary = Beech,

    secondary = Tuscany,
    onSecondary = Beech,

    error = Ruby,
    onError = Navajo,
)

@Composable
fun MinesweeperJCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}