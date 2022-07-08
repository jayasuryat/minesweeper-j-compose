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
package com.jayasuryat.minesweeperui.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.jayasuryat.util.LogCompositions
import kotlin.math.absoluteValue

@Composable
internal fun ZoomableContent(
    modifier: Modifier = Modifier,
    defaultState: ZoomPanState,
    content: @Composable (zoomPanState: ZoomPanState) -> Unit,
) {

    LogCompositions(name = "ZoomableContent")

    val scale = remember { mutableStateOf(defaultState.scale) }
    val translateX = remember { mutableStateOf(defaultState.translationX) }
    val translateY = remember { mutableStateOf(defaultState.translationY) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->

                    val scaleValue = (scale.value * zoom)
                        .coerceAtLeast(1f)
                        .coerceAtMost(3f)
                    scale.value = scaleValue

                    val modTranslateX = getScaledTranslation(
                        originalSize = this.size.width,
                        scaleFactor = scaleValue
                    )

                    val modTranslateY = getScaledTranslation(
                        originalSize = this.size.height,
                        scaleFactor = scaleValue
                    )

                    translateX.value = (translateX.value + pan.x)
                        .coerceAtLeast(-modTranslateX)
                        .coerceAtMost(modTranslateX)

                    translateY.value = (translateY.value + pan.y)
                        .coerceAtLeast(-modTranslateY)
                        .coerceAtMost(modTranslateY)
                }
            }
    ) {

        LogCompositions(name = "ZoomableContent/content")

        ZoomedContent(
            scale = scale,
            translationX = translateX,
            translationY = translateY,
            content = content,
        )
    }
}

@Composable
private fun ZoomedContent(
    modifier: Modifier = Modifier,
    scale: State<Float>,
    translationX: State<Float>,
    translationY: State<Float>,
    content: @Composable (zoomPanState: ZoomPanState) -> Unit,
) {

    Box(
        modifier = modifier,
    ) {

        content(
            ZoomPanState(
                scale = scale.value,
                translationX = translationX.value,
                translationY = translationY.value,
            )
        )
    }
}

@Stable
private fun getScaledTranslation(
    originalSize: Int,
    scaleFactor: Float,
): Float {
    val scaledWidth = originalSize * scaleFactor
    val widthDiff = (scaledWidth - originalSize).absoluteValue
    return (widthDiff / 2)
}
