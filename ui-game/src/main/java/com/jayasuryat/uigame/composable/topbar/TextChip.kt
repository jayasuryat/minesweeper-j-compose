package com.jayasuryat.uigame.composable.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun TextChip(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    contentColor: Color,
    backgroundColor: Color = contentColor.copy(alpha = 0.5f),
    strokeColor: Color = contentColor.copy(alpha = 1f),
) {

    Text(
        text = text,
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(color = backgroundColor)
            .border(
                width = 2.dp,
                color = strokeColor,
                shape = RoundedCornerShape(100),
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
private fun Preview() {

    TextChip(
        modifier = Modifier.wrapContentSize(),
        text = "I am Groot",
        contentColor = Color.Black,
        strokeColor = Color.White,
    )
}
