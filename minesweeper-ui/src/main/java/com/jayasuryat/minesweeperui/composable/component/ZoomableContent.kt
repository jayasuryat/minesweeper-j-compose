package com.jayasuryat.minesweeperui.composable.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import kotlin.math.absoluteValue

private const val EDGE_PADDING: Float = 32f

@Composable
internal fun ZoomableContent(
    modifier: Modifier = Modifier,
    content: @Composable (zoomModifier: Modifier) -> Unit,
) {

    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(0f) }
    val translateX = remember { mutableStateOf(0f) }
    val translateY = remember { mutableStateOf(0f) }

    fun updatePanState(
        size: IntSize,
        pan: Offset,
    ) {
        val actualWidth = size.width
        val scaledWidth = actualWidth * scale.value
        val widthDiff = (scaledWidth - actualWidth).absoluteValue
        val modTransX = (widthDiff / 2) + EDGE_PADDING

        val actualHeight = size.height
        val scaledHeight = actualHeight * scale.value
        val heightDiff = (scaledHeight - actualHeight).absoluteValue
        val modTransY = (heightDiff / 2) + EDGE_PADDING

        translateX.value = (translateX.value + pan.x)
            .coerceAtLeast(-modTransX)
            .coerceAtMost(modTransX)

        translateY.value = (translateY.value + pan.y)
            .coerceAtLeast(-modTransY)
            .coerceAtMost(modTransY)
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->

                    scale.value = (scale.value * zoom)
                        .coerceAtLeast(1f)
                        .coerceAtMost(3f)

                    rotationState.value += rotation
                    updatePanState(size = this.size, pan = pan)
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
                    //rotationZ = rotationState.value,
                )
        )
    }
}