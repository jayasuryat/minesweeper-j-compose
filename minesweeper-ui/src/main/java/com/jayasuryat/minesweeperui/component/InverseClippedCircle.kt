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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.theme.msColors

@Composable
internal fun InverseClippedCircle(
    modifier: Modifier = Modifier,
    iconPadding: Float,
    content: @Composable () -> Unit = {},
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds()
    ) {
        content()
    }

    val bgColor = MaterialTheme.msColors.minefield

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clipToBounds(),
        onDraw = {

            val path = getInvertedCirclePath(
                width = size.width,
                height = size.height,
                padding = iconPadding,
            )

            drawPath(path, bgColor)
        },
    )
}

@Stable
private fun getInvertedCirclePath(
    width: Float,
    height: Float,
    padding: Float,
): Path {

    val squarePath = Path()
    val circlePath = Path()

    squarePath.addRect(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = width,
            bottom = height,
        )
    )

    circlePath.addOval(
        oval = Rect(
            offset = Offset(
                x = padding,
                y = padding,
            ),
            size = Size(
                width = width - (padding * 2),
                height = height - (padding * 2),
            )
        )
    )

    return Path.combine(
        operation = PathOperation.Difference,
        path1 = squarePath,
        path2 = circlePath,
    )
}

@Preview(widthDp = 600, heightDp = 600)
@Preview(widthDp = 60, heightDp = 60)
@Composable
private fun Preview() {
    InverseClippedCircle(
        modifier = Modifier.fillMaxSize(),
        iconPadding = 32f,
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.secondary)
        )
    }
}
