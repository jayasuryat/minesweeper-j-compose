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
package com.jayasuryat.minesweeperjc.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val REVEAL_DURATION: Long = 300L

@Composable
fun LaunchWithCircularReveal(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    val isFirstLaunch = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(REVEAL_DURATION * 2)
        isFirstLaunch.value = false
    }

    Box(
        modifier = modifier,
    ) {

        content()

        if (isFirstLaunch.value) {
            CircularRevealLayout(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun CircularRevealLayout(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colors.background,
    strokeColor: Color = MaterialTheme.colors.onBackground,
) {

    val height = with(LocalConfiguration.current) {
        with(LocalDensity.current) { screenHeightDp.dp.toPx() }
    }
    val maxRadiusPx = height + 164

    val targetRadius = remember { mutableStateOf(maxRadiusPx) }
    val radius = animateFloatAsState(
        targetValue = targetRadius.value,
        animationSpec = tween(
            durationMillis = REVEAL_DURATION.toInt(),
        ),
    )

    Spacer(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {

                val drawOffset = Offset(size.width / 2, size.height / 2)

                // Circle
                drawCircle(
                    color = circleColor,
                    radius = radius.value,
                    center = drawOffset,
                )
                // Circle stroke
                drawCircle(
                    color = strokeColor,
                    radius = radius.value,
                    style = Stroke(width = 8f),
                    center = drawOffset,
                )
            },
    )

    LaunchedEffect(false) {
        targetRadius.value = 0f
    }
}
