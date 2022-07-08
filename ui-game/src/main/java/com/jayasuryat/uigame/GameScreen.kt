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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.accompanist.insets.LocalWindowInsets
import com.jayasuryat.minesweeperui.config.LocalGridAnimationConfig
import com.jayasuryat.uigame.composable.MinefieldScreen
import com.jayasuryat.uigame.composable.feedback.GameFeedback
import com.jayasuryat.uigame.composable.toggle.Toggle
import com.jayasuryat.uigame.composable.topbar.GameTopBar
import com.jayasuryat.uigame.data.model.ToggleState
import com.jayasuryat.uigame.feedback.sound.MusicManager
import com.jayasuryat.uigame.feedback.vibration.VibrationManager
import com.jayasuryat.uigame.logic.model.GameScreenStatus
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameScreen")

    DisposableEffect(key1 = true) {
        onDispose {
            viewModel.saveCurrentGameState()
        }
    }

    OnLifecycleEvent(triggerOnEvent = Lifecycle.Event.ON_STOP) {
        viewModel.saveCurrentGameState()
    }

    val screenState = remember { viewModel.screenStatus }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        AnimatedContent(
            targetState = screenState.value,
            modifier = Modifier.fillMaxSize(),
        ) { state ->

            when (state) {

                is GameScreenStatus.Loading -> Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.background),
                )

                is GameScreenStatus.Loaded -> GameScreenLoaded(
                    modifier = Modifier.fillMaxSize(),
                    screenState = state,
                    soundManager = remember { viewModel.soundManager },
                    vibrationManager = remember { viewModel.vibrationManager },
                    showToggle = remember { viewModel.shouldShowToggle },
                    toggleState = remember { viewModel.toggleState },
                    onToggleStateUpdated = viewModel::onToggleStateUpdated,
                    onRestartClicked = onRestartClicked,
                )
            }
        }
    }
}

@Composable
private fun GameScreenLoaded(
    modifier: Modifier = Modifier,
    screenState: GameScreenStatus.Loaded,
    soundManager: MusicManager,
    vibrationManager: VibrationManager,
    showToggle: State<Boolean>,
    toggleState: State<ToggleState>,
    onToggleStateUpdated: (state: ToggleState) -> Unit,
    onRestartClicked: () -> Unit,
) {

    val gameState = screenState.gameState
    val gameProgress = screenState.gameProgress

    val interactionListener = remember { screenState.interactionListener }
    val layoutInfo = remember { screenState.layoutInformation }

    LogCompositions(name = "GameScreenLoaded")

    Box(
        modifier = modifier,
    ) {

        GameFeedback(
            gameState = gameState,
            soundManger = soundManager,
            vibrationManager = vibrationManager,
        )

        CompositionLocalProvider(
            LocalGridAnimationConfig provides screenState.animationConfig,
        ) {

            MinefieldScreen(
                layoutInfo = layoutInfo,
                actionListener = interactionListener,
            )
        }

        GameTopBar(
            gameState = gameState,
            gameProgress = gameProgress,
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = TopCenter),
            onRestartClicked = onRestartClicked,
        )

        if (showToggle.value) {

            val bottomPadding = with(LocalDensity.current) {
                LocalWindowInsets.current.navigationBars.bottom.toDp()
            } + 16.dp

            Toggle(
                modifier = Modifier
                    .align(alignment = BottomCenter)
                    .padding(bottom = bottomPadding),
                toggleState = toggleState,
                onToggleStateChanged = onToggleStateUpdated,
            )
        }
    }
}
