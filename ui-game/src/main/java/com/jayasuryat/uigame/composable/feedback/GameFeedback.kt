package com.jayasuryat.uigame.composable.feedback

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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

    when (gameState.value) {

        is GameState.Idle -> Unit

        is GameState.GameStarted -> Unit

        is GameState.GameEnded.GameCompleted -> {
            vibrationManager.shortVibrationNow()
        }

        is GameState.GameEnded.GameOver -> {
            vibrationManager.mediumVibrationNow()
        }
    }
}