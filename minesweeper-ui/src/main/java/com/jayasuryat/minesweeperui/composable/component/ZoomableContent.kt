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

@Composable
internal fun ZoomableContent(
    modifier: Modifier = Modifier,
    content: @Composable (zoomModifier: Modifier) -> Unit,
) {

    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(0f) }
    val panState = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                    panState.value += pan
                }
            }
    ) {

        content(
            zoomModifier = Modifier
                .graphicsLayer(
                    scaleX = scale.value.coerceAtLeast(1f),
                    scaleY = scale.value.coerceAtLeast(1f),
                    translationX = panState.value.x,
                    translationY = panState.value.y,
                    //rotationZ = rotationState.value,
                )
        )
    }
}