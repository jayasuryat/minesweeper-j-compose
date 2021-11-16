package com.jayasuryat.minesweeperui.composable.cell

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import com.jayasuryat.minesweeperui.composable.cell.concealed.FlaggedCell
import com.jayasuryat.minesweeperui.composable.cell.concealed.UnFlaggedCell
import com.jayasuryat.minesweeperui.composable.cell.revealed.EmptyCell
import com.jayasuryat.minesweeperui.composable.cell.revealed.MineCell
import com.jayasuryat.minesweeperui.composable.cell.revealed.ValueCell
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
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

    Box(
        modifier = modifier
            .clipToBounds(),
    ) {

        AnimatedContent(
            targetState = cellState.value,
            transitionSpec = { getContentTransformAnim() },
            contentAlignment = Alignment.Center,
        ) { cell ->

            RawCellContent(
                cell = cell,
                actionListener = actionListener,
            )
        }
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
                )

            }.exhaustive
        }

    }.exhaustive
}

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