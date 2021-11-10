package com.jayasuryat.minesweeperui.composable.cell.concealed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.composable.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.composable.component.InverseClippedCircle
import com.jayasuryat.minesweeperui.composable.util.floatValue


@Composable
internal fun ConcealedCell(
    modifier: Modifier = Modifier,
) {

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {

        val minSize = minOf(maxWidth, maxHeight)
        val padding = minSize * CELL_PADDING_PERCENT

        InverseClippedCircle(iconPadding = padding.floatValue())
    }
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {
    Spacer(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)
    )
    ConcealedCell(
        modifier = Modifier.fillMaxSize(),
    )
}