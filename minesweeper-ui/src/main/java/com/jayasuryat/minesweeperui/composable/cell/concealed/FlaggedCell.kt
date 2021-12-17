package com.jayasuryat.minesweeperui.composable.cell.concealed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperui.composable.action.MinefieldActionsListener
import com.jayasuryat.minesweeperui.composable.action.NoOpActionListener
import com.jayasuryat.minesweeperui.composable.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.composable.component.InverseClippedCircle
import com.jayasuryat.minesweeperui.composable.theme.msColors
import com.jayasuryat.util.floatValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FlaggedCell(
    modifier: Modifier = Modifier,
    cell: RawCell.UnrevealedCell.FlaggedCell,
    actionListener: MinefieldActionsListener,
) {

    val haptic = LocalHapticFeedback.current

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .clipToBounds()
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {},
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    val action = MinefieldAction.OnCellLongPressed(cell)
                    actionListener.action(action)
                },
            ),
        contentAlignment = Alignment.Center
    ) {

        val minSize = minOf(maxWidth, maxHeight)
        val padding = minSize * CELL_PADDING_PERCENT

        Icon(
            modifier = modifier
                .padding(all = padding * 2),
            imageVector = Icons.Filled.Favorite,
            tint = MaterialTheme.msColors.flagIconTint,
            contentDescription = null,
        )

        InverseClippedCircle(iconPadding = padding.floatValue())
    }
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {

    val cell = RawCell.UnrevealedCell.FlaggedCell(
        cell = MineCell.ValuedCell.EmptyCell(position = Position.zero())
    )

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondary)
    )

    FlaggedCell(
        modifier = Modifier.fillMaxSize(),
        cell = cell,
        actionListener = NoOpActionListener,
    )
}
