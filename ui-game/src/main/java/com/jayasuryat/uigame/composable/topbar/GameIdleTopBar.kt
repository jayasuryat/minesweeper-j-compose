package com.jayasuryat.uigame.composable.topbar

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun GameIdleTopBar(
    modifier: Modifier = Modifier,
) {

    TextChip(
        text = "Tap a cell to start",
        modifier = modifier,
        contentColor = Color.Black,
        strokeColor = Color.White,
    )
}

@Preview
@Composable
private fun Preview() {

    GameIdleTopBar(
        modifier = Modifier.wrapContentSize(),
    )
}