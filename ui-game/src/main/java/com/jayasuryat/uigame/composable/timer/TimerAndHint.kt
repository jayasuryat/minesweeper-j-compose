package com.jayasuryat.uigame.composable.timer

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.jayasuryat.uigame.GameState

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun GameTopBar(
    modifier: Modifier = Modifier,
    gameState: State<GameState>,
    onRestartClicked: () -> Unit,
) {

    val insets = LocalWindowInsets.current

    val statusBarTop = with(LocalDensity.current) { insets.statusBars.top.toDp() } + 16.dp

    AnimatedContent(
        modifier = modifier,
        transitionSpec = { getContentTransformAnim() },
        contentAlignment = Alignment.TopCenter,
        targetState = gameState.value
    ) { state ->

        when (state) {
            is GameState.Idle -> TextChip(
                text = "Tap a cell to start",
                modifier = Modifier.padding(top = statusBarTop),
                contentColor = Color.Black,
                strokeColor = Color.White,
            )

            is GameState.GameStarted -> StartedTimer(
                startTime = state.startTime,
                modifier = Modifier.padding(top = statusBarTop),
            )

            is GameState.GameEnded.GameCompleted -> {
                Row(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(top = statusBarTop)
                ) {

                    TextChip(
                        text = "Done in ${formatTime(state.elapsedDuration)}",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        contentColor = Color.Green
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        tint = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .align(alignment = Alignment.CenterVertically)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = CircleShape,
                            )
                            .clickable { onRestartClicked() }
                            .padding(8.dp),
                        contentDescription = null,
                    )
                }
            }

            is GameState.GameEnded.GameOver -> {
                Row(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(top = statusBarTop)
                ) {

                    TextChip(
                        text = "Game over",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        contentColor = Color.Red
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        tint = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .align(alignment = Alignment.CenterVertically)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = CircleShape,
                            )
                            .clickable { onRestartClicked() }
                            .padding(8.dp),
                        contentDescription = null,
                    )
                }
            }
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

@Stable
internal fun formatTime(millis: Long): String {
    val secs = millis / 1000
    return String.format("%02d:%02d", secs % 3600 / 60, secs % 60)
}