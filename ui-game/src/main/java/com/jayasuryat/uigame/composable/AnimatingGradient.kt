/*
 * Copyright 2022 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    val offset = 2000f

    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = offset,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 14000,
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
