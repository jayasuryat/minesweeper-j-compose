/*
 * Copyright 2021 Jaya Surya Thotapalli
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
package com.jayasuryat.uigame.composable.feedback

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.jayasuryat.uigame.feedback.MusicManager
import com.jayasuryat.uigame.feedback.VibrationManager
import com.jayasuryat.uigame.logic.GameState
import com.jayasuryat.util.LogCompositions

@Composable
internal fun GameFeedback(
    gameState: State<GameState>,
) {

    LogCompositions(name = "GameFeedback")

    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }
    val musicManager = remember { MusicManager(context) }

    when (gameState.value) {

        is GameState.Idle -> Unit

        is GameState.GameStarted -> Unit

        is GameState.GameEnded.GameCompleted -> {
            musicManager.success()
            vibrationManager.shortVibrationNow()
        }

        is GameState.GameEnded.GameOver -> {
            musicManager.failure()
            vibrationManager.mediumVibrationNow()
        }
    }
}
