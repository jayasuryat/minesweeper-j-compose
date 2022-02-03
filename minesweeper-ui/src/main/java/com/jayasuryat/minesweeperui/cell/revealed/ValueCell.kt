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
package com.jayasuryat.minesweeperui.cell.revealed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperui.action.CellInteraction
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.minesweeperui.action.NoOpInteractionListener
import com.jayasuryat.minesweeperui.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.cell.VALUE_CELL_TEXT_COVER_PERCENT
import com.jayasuryat.minesweeperui.component.InverseClippedCircle
import com.jayasuryat.minesweeperui.theme.msColors
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.dp
import com.jayasuryat.util.floatValue
import com.jayasuryat.util.sp

@Composable
internal fun ValueCell(
    modifier: Modifier = Modifier,
    cell: MineCell.ValuedCell.Cell,
    actionListener: CellInteractionListener,
) {

    LogCompositions(name = "ValueCell")

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                val action = CellInteraction.OnValueCellClicked(cell = cell)
                actionListener.action(action)
            },
        contentAlignment = Alignment.Center
    ) {

        val fontSize = getFontSize(width = maxWidth, height = maxHeight)
        val padding = getPadding(width = maxWidth, height = maxHeight)

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .padding((padding - 2f).dp())
                .background(
                    color = MaterialTheme.msColors.minefield
                        .copy(alpha = getAlphaForValue(cell.value))
                )
        )

        Text(
            text = cell.value.toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.W800,
            textAlign = TextAlign.Center,
            color = MaterialTheme.msColors.text,
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(bottom = maxHeight / 20),
        )

        InverseClippedCircle(iconPadding = padding)
    }
}

@Stable
private fun getAlphaForValue(value: Int): Float {

    return when (value) {
        1 -> 0.85f
        2 -> 0.65f
        else -> 0.4f
    }
}

@Composable
@Stable
@ReadOnlyComposable
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
@ReadOnlyComposable
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
        actionListener = NoOpInteractionListener,
    )
}
