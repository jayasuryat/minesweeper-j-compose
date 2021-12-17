package com.jayasuryat.uigame.composable.topbar

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jayasuryat.uigame.logic.GameState

internal class GameTopBarParamProvider : PreviewParameterProvider<GameState> {

    override val values: Sequence<GameState>
        get() = sequenceOf(
            GameState.Idle,
            GameState.GameStarted.now(),
            GameState.GameEnded.GameOver.now(),
            GameState.GameEnded.GameCompleted.now(startTime = System.currentTimeMillis()),
        )
}
