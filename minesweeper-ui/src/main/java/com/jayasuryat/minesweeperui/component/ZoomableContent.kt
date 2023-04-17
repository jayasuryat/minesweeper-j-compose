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

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import com.jayasuryat.util.LogCompositions
import kotlin.math.PI
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
                detectTransformGestures(
                    pass = PointerEventPass.Initial,
                ) { _, pan: Offset, zoom: Float, _, _, changes: List<PointerInputChange> ->

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

                    // Consume touch when multiple fingers down
                    // This prevents click and long click if your finger touches a
                    // button while pinch gesture is being invoked
                    val size = changes.size
                    if (size > 1) {
                        changes.forEach { it.consume() }
                    }
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

// Code sourced from https://stackoverflow.com/a/76021552/10323788

private suspend fun PointerInputScope.detectTransformGestures(
    panZoomLock: Boolean = false,
    consume: Boolean = true,
    pass: PointerEventPass = PointerEventPass.Main,
    onGestureStart: (PointerInputChange) -> Unit = {},
    onGestureEnd: (PointerInputChange) -> Unit = {},
    onGesture: (
        centroid: Offset,
        pan: Offset,
        zoom: Float,
        rotation: Float,
        mainPointer: PointerInputChange,
        changes: List<PointerInputChange>
    ) -> Unit,
) {

    awaitEachGesture {
        var rotation = 0f
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        var lockedToPanZoom = false

        // Wait for at least one pointer to press down, and set first contact position
        val down: PointerInputChange = awaitFirstDown(
            requireUnconsumed = false,
            pass = pass
        )
        onGestureStart(down)

        var pointer = down
        // Main pointer is the one that is down initially
        var pointerId = down.id

        do {
            val event = awaitPointerEvent(pass = pass)

            // If any position change is consumed from another PointerInputChange
            // or pointer count requirement is not fulfilled
            val canceled = event.changes.any { it.isConsumed }

            if (!canceled) {

                // Get pointer that is down, if first pointer is up
                // get another and use it if other pointers are also down
                // event.changes.first() doesn't return same order
                val pointerInputChange = event.changes
                    .firstOrNull { it.id == pointerId }
                    ?: event.changes.first()

                // Next time will check same pointer with this id
                pointerId = pointerInputChange.id
                pointer = pointerInputChange

                val zoomChange = event.calculateZoom()
                val rotationChange = event.calculateRotation()
                val panChange = event.calculatePan()

                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    rotation += rotationChange
                    pan += panChange

                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = kotlin.math.abs(1 - zoom) * centroidSize
                    val rotationMotion =
                        kotlin.math.abs(rotation * PI.toFloat() * centroidSize / 180f)
                    val panMotion = pan.getDistance()

                    if (zoomMotion > touchSlop ||
                        rotationMotion > touchSlop ||
                        panMotion > touchSlop
                    ) {
                        pastTouchSlop = true
                        lockedToPanZoom = panZoomLock && rotationMotion < touchSlop
                    }
                }

                if (pastTouchSlop) {
                    val centroid = event.calculateCentroid(useCurrent = false)
                    val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                    if (effectiveRotation != 0f ||
                        zoomChange != 1f ||
                        panChange != Offset.Zero
                    ) {
                        onGesture(
                            centroid,
                            panChange,
                            zoomChange,
                            effectiveRotation,
                            pointer,
                            event.changes
                        )
                    }

                    if (consume) {
                        event.changes.forEach { if (it.positionChanged()) it.consume() }
                    }
                }
            }
        } while (!canceled && event.changes.any { it.pressed })

        onGestureEnd(pointer)
    }
}
