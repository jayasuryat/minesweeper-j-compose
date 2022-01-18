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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell.RevealedCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell.UnrevealedCell
import com.jayasuryat.minesweeperui.action.MinefieldActionsListener
import com.jayasuryat.minesweeperui.cell.concealed.FlaggedCell
import com.jayasuryat.minesweeperui.cell.concealed.UnFlaggedCell
import com.jayasuryat.minesweeperui.cell.revealed.EmptyCell
import com.jayasuryat.minesweeperui.cell.revealed.MineCell
import com.jayasuryat.minesweeperui.cell.revealed.ValueCell
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.exhaustive
import com.jayasuryat.minesweeperengine.model.cell.RawCell as RawCellData

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun RawCell(
    modifier: Modifier,
    cellState: State<RawCellData>,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "RawCell")

    // Commented out AnimatedContent as it is eating up perf. Some animations are getting triggered
    // redundantly due how some models are modeled. i.e., RawCell.UnrevealedCell

    /*
    AnimatedContent(
        modifier = modifier
            .clipToBounds(),
        targetState = cellState.value,
        transitionSpec = { getContentTransformAnim() },
        contentAlignment = Alignment.Center,
    ) { cell ->

        LogCompositions(name = "RawCell\$AnimatedContent")

        RawCellContent(
            cell = cell,
            actionListener = actionListener,
        )
    }
    */

    Box(
        modifier = modifier
            .clipToBounds(),
        contentAlignment = Alignment.Center,
    ) {

        LogCompositions(name = "RawCell\$AnimatedContent")

        RawCellContent(
            cell = cellState.value,
            actionListener = actionListener,
        )
    }
}

@Composable
private fun RawCellContent(
    cell: RawCellData,
    actionListener: MinefieldActionsListener,
) {

    LogCompositions(name = "RawCellContent")

    when (cell) {

        is UnrevealedCell -> when (cell) {

            is UnrevealedCell.FlaggedCell -> FlaggedCell(
                cell = cell,
                actionListener = actionListener,
            )

            is UnrevealedCell.UnFlaggedCell -> UnFlaggedCell(
                cell = cell,
                actionListener = actionListener,
            )
        }.exhaustive

        is RevealedCell -> {

            when (val revealedCell = cell.cell) {

                is MineCell.Mine -> MineCell(modifier = Modifier.fillMaxSize())

                is MineCell.ValuedCell.EmptyCell -> EmptyCell(modifier = Modifier.fillMaxSize())

                is MineCell.ValuedCell.Cell -> ValueCell(
                    modifier = Modifier.fillMaxSize(),
                    cell = revealedCell,
                    actionListener = actionListener,
                )
            }.exhaustive
        }
    }.exhaustive
}

/*
@ExperimentalAnimationApi
private fun getContentTransformAnim(): ContentTransform {

    val enterAnim = fadeIn(
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 20
        )
    )

    val exitAnim = fadeOut(
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 330,
        )
    )

    return ContentTransform(enterAnim, exitAnim)
}
*/
