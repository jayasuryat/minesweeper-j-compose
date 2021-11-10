package com.jayasuryat.minesweeperjc.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode.Companion.Mirror
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AnimatingGradient(
    modifier: Modifier = Modifier,
) {

    val offset = 4000f

    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = offset,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val colors = listOf(
        Color.Magenta,
        Color.Cyan,
        Color.Magenta,
    )

    val brush = linearGradient(
        colors = colors,
        start = Offset(x = animatedOffset.value, y = animatedOffset.value),
        end = Offset(x = animatedOffset.value + offset, y = animatedOffset.value + offset),
        tileMode = Mirror
    )

    Spacer(
        modifier = modifier
            .background(brush = brush)
            .clipToBounds()
    )
}