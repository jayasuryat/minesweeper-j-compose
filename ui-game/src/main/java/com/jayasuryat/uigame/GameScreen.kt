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
package com.jayasuryat.uigame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.jayasuryat.minesweeperui.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.feedback.GameFeedback
import com.jayasuryat.uigame.composable.toggle.Toggle
import com.jayasuryat.uigame.composable.topbar.GameTopBar
import com.jayasuryat.uigame.logic.ToggleState
import com.jayasuryat.util.LogCompositions

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onToggleStateChanged: (toggleState: ToggleState) -> Unit,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

    LaunchedEffect(key1 = null) {
        viewModel.loadToggleState()
    }

    val actionsListener = remember { viewModel.actionLister }
    val layoutInfo = remember { GridLayoutInformation.from(statefulGrid = viewModel.statefulGrid) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        LogCompositions(name = "GameScreen\$Box")

        GameFeedback(
            gameState = viewModel.gameState
        )

        MinefieldScreen(
            layoutInfo = layoutInfo,
            actionListener = actionsListener,
        )

        GameTopBar(
            gameState = viewModel.gameState,
            gameProgress = viewModel.gameProgress,
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = TopCenter),
            onRestartClicked = onRestartClicked,
        )

        val bottomPadding = with(LocalDensity.current) {
            LocalWindowInsets.current.navigationBars.bottom.toDp()
        } + 16.dp

        Toggle(
            modifier = Modifier
                .align(alignment = BottomCenter)
                .padding(bottom = bottomPadding),
            toggleState = viewModel.toggleState,
            onToggleStateChanged = { toggleState ->
                onToggleStateChanged(toggleState)
                viewModel.onToggleStateUpdated(toggleState)
            },
        )
    }
}
