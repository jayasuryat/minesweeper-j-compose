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
package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.composable.theme.msColors
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.dp

@Composable
internal fun InverseClippedBox(
    modifier: Modifier = Modifier,
    parentSize: Size,
    contentSize: Size,
    padding: Size = Size.Zero,
    innerInset: Size = Size.Zero,
) {

    LogCompositions(name = "InverseClippedBox")

    val width = parentSize.width.dp()
    val height = parentSize.height.dp()

    val bgColor = MaterialTheme.msColors.minefield

    Canvas(
        modifier = modifier
            .size(width = width, height = height),
        onDraw = {

            val path = getInvertedBoxPath(
                parentSize = parentSize,
                contentSize = contentSize,
                padding = padding,
                innerInset = innerInset,
            )

            drawPath(
                path = path,
                color = bgColor,
            )
        },
    )
}

@Stable
private fun getInvertedBoxPath(
    parentSize: Size,
    contentSize: Size,
    padding: Size,
    innerInset: Size,
): Path {

    val rootSquarePath = Path()
    val innerSquarePath = Path()

    rootSquarePath.addRect(
        rect = Rect(
            offset = Offset.Zero - Offset(x = padding.width, y = padding.height),
            size = Size(
                width = parentSize.width + (padding.width * 2),
                height = parentSize.height + (padding.height * 2),
            ),
        )
    )

    val contentOffset = Offset(
        x = (parentSize.width / 2) - (contentSize.width / 2) + innerInset.width,
        y = (parentSize.height / 2) - (contentSize.height / 2) + innerInset.height,
    )

    innerSquarePath.addRect(
        rect = Rect(
            offset = contentOffset,
            size = Size(
                width = contentSize.width - (innerInset.width * 2),
                height = contentSize.height - (innerInset.height * 2),
            ),
        )
    )

    return Path.combine(
        operation = PathOperation.Difference,
        path1 = rootSquarePath,
        path2 = innerSquarePath,
    )
}

@Preview
@Composable
private fun Preview() {
    InverseClippedBox(
        parentSize = Size(width = 100f, height = 400f),
        contentSize = Size(width = 50f, height = 200f),
    )
}
