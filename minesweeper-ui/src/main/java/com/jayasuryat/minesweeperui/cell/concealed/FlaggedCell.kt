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
package com.jayasuryat.minesweeperui.cell.concealed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import com.jayasuryat.minesweeperui.action.CellInteraction
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.minesweeperui.action.NoOpInteractionListener
import com.jayasuryat.minesweeperui.theme.msColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FlaggedCell(
    modifier: Modifier = Modifier,
    cell: RawCell.UnrevealedCell.FlaggedCell,
    actionListener: CellInteractionListener,
) {

    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.primary)
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    // TODO: 20/01/22 Handle haptics properly
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    val action = CellInteraction.OnCellClicked(cell)
                    actionListener.action(action)
                },
                onLongClick = {
                    // TODO: 20/01/22 Handle haptics properly
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    val action = CellInteraction.OnCellLongPressed(cell)
                    actionListener.action(action)
                },
            ),
        contentAlignment = Alignment.Center,
    ) {

        Icon(
            modifier = Modifier.fillMaxSize(0.70f),
            imageVector = Icons.Filled.Favorite,
            tint = MaterialTheme.msColors.flagIconTint,
            contentDescription = null,
        )
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
        actionListener = NoOpInteractionListener,
    )
}
