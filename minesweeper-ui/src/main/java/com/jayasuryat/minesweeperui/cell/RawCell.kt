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
package com.jayasuryat.minesweeperui.cell

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.minesweeperui.cell.concealed.FlaggedCell
import com.jayasuryat.minesweeperui.cell.concealed.UnFlaggedCell
import com.jayasuryat.minesweeperui.cell.interaction.CellInteractionMapper
import com.jayasuryat.minesweeperui.cell.interaction.DisplayCellInteractionListener
import com.jayasuryat.minesweeperui.cell.revealed.EmptyCell
import com.jayasuryat.minesweeperui.cell.revealed.MineCell
import com.jayasuryat.minesweeperui.cell.revealed.ValueCell
import com.jayasuryat.minesweeperui.model.DisplayCell
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.exhaustive

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun RawCell(
    modifier: Modifier,
    displayCell: DisplayCell,
    interactionListener: CellInteractionListener,
) {

    LogCompositions(name = "RawCell")

    val listenerMapper = remember {
        CellInteractionMapper(
            position = displayCell.position,
            listener = interactionListener,
        )
    }

    // Commented out AnimatedContent as it is eating up perf. Some animations are getting triggered
    // redundantly due how some models are modeled. i.e., RawCell.UnrevealedCell

    val contentTransform = remember { getContentTransformAnim() }

    AnimatedContent(
        modifier = modifier,
        targetState = displayCell.cellState.value,
        transitionSpec = { contentTransform },
        contentAlignment = Alignment.Center,
    ) { cell ->

        LogCompositions(name = "RawCell\$Box")

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            RawCellContent(
                modifier = Modifier.fillMaxSize(1 - CELL_GAP_PERCENT),
                displayCell = cell,
                listener = listenerMapper,
            )
        }
    }
}

@Composable
private fun RawCellContent(
    modifier: Modifier = Modifier,
    displayCell: DisplayCell.Cell,
    listener: DisplayCellInteractionListener,
) {

    LogCompositions(name = "RawCellContent")

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        when (displayCell) {

            DisplayCell.Cell.UnFlaggedCell -> UnFlaggedCell(
                modifier = Modifier.fillMaxSize(),
                onClick = listener::onUnFlaggedCellClicked,
                onLongPressed = listener::onUnFlaggedCellLongPressed,
            )

            DisplayCell.Cell.FlaggedCell -> FlaggedCell(
                modifier = Modifier.fillMaxSize(),
                onClick = listener::onFlaggedCellClicked,
                onLongPressed = listener::onFlaggedCellLongPressed,
            )

            DisplayCell.Cell.Mine -> MineCell(
                modifier = Modifier.fillMaxSize(),
            )

            DisplayCell.Cell.EmptyCell -> EmptyCell(
                modifier = Modifier.fillMaxSize(),
            )

            is DisplayCell.Cell.ValueCell -> ValueCell(
                modifier = Modifier.fillMaxSize(),
                displayCell = displayCell,
                onClick = listener::onValueCellClicked,
            )
        }.exhaustive
    }
}

@ExperimentalAnimationApi
private fun getContentTransformAnim(): ContentTransform {

    val enterAnim = scaleIn(
        animationSpec = tween(
            durationMillis = 160,
            delayMillis = 80
        )
    )

    val exitAnim = scaleOut(
        animationSpec = tween(
            durationMillis = 160,
            delayMillis = 0,
        )
    )

    return ContentTransform(enterAnim, exitAnim)
}
