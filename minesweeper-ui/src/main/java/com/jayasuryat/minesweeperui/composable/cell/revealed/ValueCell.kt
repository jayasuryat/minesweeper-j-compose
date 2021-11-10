package com.jayasuryat.minesweeperui.composable.cell.revealed

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperui.composable.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.composable.cell.VALUE_CELL_TEXT_COVER_PERCENT
import com.jayasuryat.minesweeperui.composable.component.InverseClippedCircle
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.util.floatValue
import com.jayasuryat.minesweeperui.composable.util.sp

@Composable
internal fun ValueCell(
    modifier: Modifier = Modifier,
    cell: MineCell.Cell,
) {

    LogCompositions(name = "ValueCell")

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {

        val minSize = minOf(maxWidth, maxHeight)
        val padding = minSize * CELL_PADDING_PERCENT
        val availableSize = minSize * VALUE_CELL_TEXT_COVER_PERCENT
        val fontSize = availableSize.sp()

        Text(
            text = cell.value.toString(),
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .wrapContentSize(),
        )

        InverseClippedCircle(iconPadding = padding.floatValue())
    }
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {
    ValueCell(
        cell = MineCell.Cell(value = 1, position = Position.zero()),
        modifier = Modifier.fillMaxSize(),
    )
}