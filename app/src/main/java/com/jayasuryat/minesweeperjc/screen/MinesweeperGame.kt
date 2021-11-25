package com.jayasuryat.minesweeperjc.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.uigame.GameScreen
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
        transitionSpec = {
            val duration = 1000
            fadeIn(animationSpec = tween(durationMillis = duration)) with
                    fadeOut(animationSpec = tween(durationMillis = duration,
                        delayMillis = duration))
        }
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