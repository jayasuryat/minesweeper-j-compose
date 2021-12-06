package com.jayasuryat.uigame.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode.Companion.Mirror

@Composable
internal fun AnimatingGradient(
    modifier: Modifier = Modifier,
    colors: List<Color> = getGradientColors(),
) {

    val offset = 1000f

    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = offset,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        )
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

@Composable
@ReadOnlyComposable
private fun getGradientColors(): List<Color> {

    return listOf(
        MaterialTheme.colors.primary,
        MaterialTheme.colors.secondary,
        MaterialTheme.colors.primary,
    )
}