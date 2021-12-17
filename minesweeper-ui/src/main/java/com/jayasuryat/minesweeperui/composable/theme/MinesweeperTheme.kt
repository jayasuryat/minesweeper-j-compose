package com.jayasuryat.minesweeperui.composable.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
public fun MinesweeperTheme(
    colors: MinesweeperColors = DefaultColors,
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}

internal val LocalColors = staticCompositionLocalOf { DefaultColors }

@Suppress("unused")
internal val MaterialTheme.msColors: MinesweeperColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
