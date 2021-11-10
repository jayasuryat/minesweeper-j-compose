package com.jayasuryat.minesweeperui.composable.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.tooling.preview.Preview

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

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clipToBounds()
            .background(color = Color.Transparent),
        onDraw = {

            val path = getInvertedCirclePath(
                width = size.width,
                height = size.height,
                padding = iconPadding,
            )

            drawPath(path, Color.Black)
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

    squarePath.addRect(rect = Rect(
        left = 0f,
        top = 0f,
        right = width,
        bottom = height,
    ))

    circlePath.addOval(oval = Rect(
        offset = Offset(
            x = padding,
            y = padding,
        ),
        size = Size(
            width = width - (padding * 2),
            height = height - (padding * 2),
        )
    ))

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
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
        )
    }
}