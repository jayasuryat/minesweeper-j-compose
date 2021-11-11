package com.jayasuryat.minesweeperui.composable.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.util.dp

@Composable
internal fun InverseClippedBox(
    modifier: Modifier = Modifier,
    parentSize: Size,
    contentSize: Size,
    padding: Size = Size.Zero,
    innerInset: Size = Size.Zero,
) {

    LogCompositions(name = "inside InverseClippedBox")

    val width = parentSize.width.dp()
    val height = parentSize.height.dp()

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .background(color = Color.Transparent),
        onDraw = {

            val path = getInvertedBoxPath(
                parentSize = parentSize,
                contentSize = contentSize,
                padding = padding,
                innerInset = innerInset,
            )

            drawPath(path, Color.Black)
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

    rootSquarePath.addRect(rect = Rect(
        offset = Offset.Zero - Offset(x = padding.width, y = padding.height),
        size = Size(
            width = parentSize.width + (padding.width * 2),
            height = parentSize.height + (padding.height * 2),
        ),
    ))

    val contentOffset = Offset(
        x = (parentSize.width / 2) - (contentSize.width / 2) + innerInset.width,
        y = (parentSize.height / 2) - (contentSize.height / 2) + innerInset.height,
    )

    innerSquarePath.addRect(rect = Rect(
        offset = contentOffset,
        size = Size(
            width = contentSize.width - (innerInset.width * 2),
            height = contentSize.height - (innerInset.height * 2),
        ),
    ))

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