package com.jayasuryat.minesweeperui.composable.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.absoluteValue

@Composable
internal fun ZoomableContent(
    modifier: Modifier = Modifier,
    content: @Composable (zoomModifier: Modifier) -> Unit,
) {

    val scale = remember { mutableStateOf(1f) }
    val translateX = remember { mutableStateOf(0f) }
    val translateY = remember { mutableStateOf(0f) }

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

        content(
            zoomModifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                    translationX = translateX.value,
                    translationY = translateY.value,
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