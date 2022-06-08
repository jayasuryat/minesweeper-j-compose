package com.jayasuryat.minesweeperui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

@Composable
internal fun WithSizeOf(
    contentToMeasure: @Composable () -> Unit,
    content: @Composable (height: Dp, width: Dp) -> Unit,
) {

    SubcomposeLayout { constraints ->

        val placeable = subcompose("sampleText") {
            contentToMeasure()
        }[0].measure(Constraints())

        val height = placeable.height.toDp()
        val width = placeable.width.toDp()

        val contentPlaceable = subcompose("content") {
            content(height, width)
        }[0].measure(constraints)

        layout(
            width = contentPlaceable.width,
            height = contentPlaceable.height,
        ) {
            contentPlaceable.place(0, 0)
        }
    }
}
