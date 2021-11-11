package com.jayasuryat.minesweeperui.composable.cell.revealed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperui.composable.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.composable.cell.VALUE_CELL_TEXT_COVER_PERCENT
import com.jayasuryat.minesweeperui.composable.component.InverseClippedCircle
import com.jayasuryat.minesweeperui.composable.component.LogCompositions
import com.jayasuryat.minesweeperui.composable.util.dp
import com.jayasuryat.minesweeperui.composable.util.floatValue
import com.jayasuryat.minesweeperui.composable.util.sp

@Composable
internal fun ValueCell(
    modifier: Modifier = Modifier,
    cell: MineCell.ValuedCell.Cell,
) {

    LogCompositions(name = "ValueCell")

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {

        val fontSize = getFontSize(width = maxWidth, height = maxHeight)
        val padding = getPadding(width = maxWidth, height = maxHeight)

        Spacer(modifier = Modifier
            .fillMaxSize()
            .padding((padding - 1f).dp())
            .background(color = Color.Red)
        )

        Text(
            text = cell.value.toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(bottom = maxHeight / 20, end = maxWidth / 20),
        )

        InverseClippedCircle(iconPadding = padding)
    }
}

@Composable
@Stable
private fun getFontSize(
    width: Dp,
    height: Dp,
): TextUnit {

    val minSize = minOf(width, height)
    val availableSize = minSize * VALUE_CELL_TEXT_COVER_PERCENT
    return availableSize.sp()
}

@Composable
@Stable
private fun getPadding(
    width: Dp,
    height: Dp,
): Float {
    val minSize = minOf(width, height)
    return (minSize * CELL_PADDING_PERCENT).floatValue()
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {
    ValueCell(
        cell = MineCell.ValuedCell.Cell(value = 1, position = Position.zero()),
        modifier = Modifier.fillMaxSize(),
    )
}