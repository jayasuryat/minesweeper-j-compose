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
package com.jayasuryat.uigame.composable.topbar.started

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayasuryat.uigame.composable.topbar.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
internal fun TickerChip(
    modifier: Modifier = Modifier,
    startTime: Long,
) {

    val elapsedDuration = remember { mutableStateOf(System.currentTimeMillis() - startTime) }

    LaunchedEffect(key1 = startTime) {
        while (this.isActive) {
            delay(1000)
            elapsedDuration.value = System.currentTimeMillis() - startTime
        }
    }

    Row(
        modifier = modifier
            .width(90.dp)
            .clip(RoundedCornerShape(100))
            .background(color = MaterialTheme.colors.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(100),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {

        val text = formatTime(elapsedDuration.value)

        AnimatingCharacter(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            character = text[0]
        )

        AnimatingCharacter(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            character = text[1]
        )

        Text(
            text = " : ",
            style = MaterialTheme.typography.body1.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
            )
        )

        AnimatingCharacter(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            character = text[3]
        )

        AnimatingCharacter(
            modifier = Modifier
                .wrapContentSize()
                .weight(1f),
            character = text[4]
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatingCharacter(
    modifier: Modifier = Modifier,
    character: Char,
) {

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
        targetState = character,
    ) { targetState ->

        Text(
            text = targetState.toString(),
            style = MaterialTheme.typography.body1.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {

    TickerChip(
        startTime = System.currentTimeMillis()
    )
}
