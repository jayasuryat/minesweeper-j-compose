package com.jayasuryat.minesweeperjc.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import com.jayasuryat.uigame.GameScreen
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.util.LogCompositions
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun MinesweeperGame() {

    LogCompositions(name = "MinesweeperGame")

    val gameConfiguration = remember {
        mutableStateOf(
            GameConfiguration(
                gameId = UUID.randomUUID().toString(),
                rows = 10,
                columns = 10,
                mines = 10,
            )
        )
    }

    AnimatedContent(
        targetState = gameConfiguration.value,
        transitionSpec = { getContentTransform() }
    ) { configuration ->

        key(configuration.gameId) {

            GameScreen(
                gameConfiguration = configuration,
                onRestartClicked = {
                    gameConfiguration.value =
                        gameConfiguration.value.copy(gameId = UUID.randomUUID().toString())
                }
            )
        }
    }
}

@ExperimentalAnimationApi
private fun getContentTransform(): ContentTransform {

    val duration = 1000
    val xOff = 0
    val yOff = 2000

    val enter = slideIn(
        initialOffset = { IntOffset(x = -xOff, y = -yOff) },
        animationSpec = tween(durationMillis = duration)
    ) + fadeIn(
        animationSpec = tween(durationMillis = duration)
    )

    val exit = slideOut(
        targetOffset = { IntOffset(x = xOff, y = yOff) },
        animationSpec = tween(durationMillis = duration)
    ) + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )

    return enter with exit
}