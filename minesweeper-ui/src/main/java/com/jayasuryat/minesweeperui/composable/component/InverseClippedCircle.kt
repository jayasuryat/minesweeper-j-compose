package com.jayasuryat.minesweeperui.composable.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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

            val squarePath = Path()
            val circlePath = Path()

            squarePath.addRect(rect = Rect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height,
            ))

            circlePath.addOval(oval = Rect(
                offset = Offset(
                    x = iconPadding,
                    y = iconPadding,
                ),
                size = Size(
                    width = size.width - (iconPadding * 2),
                    height = size.height - (iconPadding * 2),
                )
            ))

            val finalPath = Path.combine(
                operation = PathOperation.Difference,
                path1 = squarePath,
                path2 = circlePath,
            )

            drawPath(finalPath, Color.Black)
        },
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