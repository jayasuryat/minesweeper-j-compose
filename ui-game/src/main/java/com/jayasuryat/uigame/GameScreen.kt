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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import com.jayasuryat.minesweeperui.composable.grid.GridLayoutInformation
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.feedback.GameFeedback
import com.jayasuryat.uigame.composable.topbar.GameTopBar
import com.jayasuryat.util.LogCompositions

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

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
    }
}
