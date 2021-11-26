package com.jayasuryat.uigame.composable.topbar.started

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jayasuryat.uigame.logic.GameProgress
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun GameProgressChip(
    modifier: Modifier = Modifier,
    gameProgress: State<GameProgress>,
) {

    LogCompositions(name = "GameProgressChip")

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(color = Color.Black.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(100),
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        LogCompositions(name = "GameProgressChip\$Row")

        AnimatingFlaggedCount(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            gameProgress = gameProgress,
        )

        Spacer(modifier = Modifier.width(8.dp))

        MineIcon(modifier = Modifier
            .size(16.dp)
            .align(alignment = Alignment.CenterVertically)
        )
    }
}

@Composable
@ExperimentalAnimationApi
private fun AnimatingFlaggedCount(
    modifier: Modifier = Modifier,
    gameProgress: State<GameProgress>,
) {

    LogCompositions(name = "AnimatingFlaggedCount")

    AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            slideIn(
                initialOffset = { fullSize -> IntOffset(x = 0, y = -fullSize.height) },
                animationSpec = tween(durationMillis = 300),
            ) with slideOut(
                targetOffset = { fullSize -> IntOffset(x = 0, y = fullSize.height) },
                animationSpec = tween(durationMillis = 300)
            )
        },
        targetState = gameProgress.value,
    ) { progress ->

        Text(
            text = "${progress.totalMinesCount - progress.flaggedMinesCount}",
            color = Color.White,
        )
    }
}

@Composable
private fun MineIcon(
    modifier: Modifier = Modifier,
) {

    LogCompositions(name = "TotalMineCount")

    Icon(
        modifier = modifier,
        imageVector = Icons.Filled.Favorite,
        tint = Color.White,
        contentDescription = null,
    )
}

@Preview
@Composable
private fun Preview() {

    val gameProgress = remember {
        mutableStateOf(GameProgress(
            totalMinesCount = 10,
            flaggedMinesCount = 7,
        ))
    }

    GameProgressChip(
        gameProgress = gameProgress,
    )
}