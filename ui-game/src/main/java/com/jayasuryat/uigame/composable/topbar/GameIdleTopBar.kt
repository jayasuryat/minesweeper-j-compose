package com.jayasuryat.uigame.composable.topbar

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun GameIdleTopBar(
    modifier: Modifier = Modifier,
) {

    TextChip(
        text = "Tap a cell to start",
        modifier = modifier,
        contentColor = MaterialTheme.colors.background,
        textColor = MaterialTheme.colors.onBackground,
        strokeColor = MaterialTheme.colors.onBackground,
    )
}

@Preview
@Composable
private fun Preview() {

    GameIdleTopBar(
        modifier = Modifier.wrapContentSize(),
    )
}