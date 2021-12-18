package com.jayasuryat.uigame.composable.topbar

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.jayasuryat.uigame.composable.topbar.started.GameStartedTopBar
import com.jayasuryat.uigame.logic.GameProgress
import com.jayasuryat.uigame.logic.GameState
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun GameTopBar(
    modifier: Modifier = Modifier,
    gameState: State<GameState>,
    gameProgress: State<GameProgress>,
    onRestartClicked: () -> Unit,
) {

    LogCompositions(name = "GameTopBar")

    val statusBarTop = with(LocalDensity.current) {
        LocalWindowInsets.current.statusBars.top.toDp()
    } + 16.dp

    AnimatedContent(
        modifier = modifier,
        transitionSpec = { getContentTransformAnim() },
        contentAlignment = Alignment.TopCenter,
        targetState = gameState.value
    ) { state ->

        LogCompositions(name = "GameTopBar\$AnimatedContent")

        when (state) {

            is GameState.Idle -> GameIdleTopBar(
                modifier = Modifier.padding(top = statusBarTop),
            )

            is GameState.GameStarted -> GameStartedTopBar(
                modifier = Modifier.padding(top = statusBarTop),
                startTime = state.startTime,
                gameProgress = gameProgress,
            )

            is GameState.GameEnded.GameCompleted -> GameCompletedTopBar(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = statusBarTop),
                elapsedDuration = state.elapsedDuration,
                onRestartClicked = onRestartClicked,
            )

            is GameState.GameEnded.GameOver -> GameOverTopBar(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = statusBarTop),
                onRestartClicked = onRestartClicked,
            )
        }
    }
}

@ExperimentalAnimationApi
private fun getContentTransformAnim(): ContentTransform {

    val topOffset = -100

    val enterAnim = slideIn(
        initialOffset = { IntOffset(x = 0, y = topOffset) },
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 310
        ),
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 310,
        ),
    )

    val exitAnim = slideOut(
        targetOffset = { IntOffset(x = 0, y = topOffset) },
        animationSpec = tween(durationMillis = 300)
    ) + fadeOut(
        animationSpec = tween(durationMillis = 300),
    )

    return ContentTransform(enterAnim, exitAnim)
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(GameTopBarParamProvider::class) gameState: GameState,
) {

    val state = remember { mutableStateOf(gameState) }
    val progress = remember {
        mutableStateOf(
            GameProgress(
                totalMinesCount = 10,
                flaggedMinesCount = 7
            )
        )
    }

    GameTopBar(
        modifier = Modifier.wrapContentSize(),
        gameState = state,
        gameProgress = progress,
        onRestartClicked = {},
    )
}
