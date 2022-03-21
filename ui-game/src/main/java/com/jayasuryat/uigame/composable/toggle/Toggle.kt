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
package com.jayasuryat.uigame.composable.toggle

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperui.R
import com.jayasuryat.uigame.logic.ToggleState
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Toggle(
    modifier: Modifier,
    toggleState: State<ToggleState>,
    onToggleStateChanged: (newState: ToggleState) -> Unit,
) {

    LogCompositions(name = "Toggle")

    fun updateState(newState: ToggleState) {
        onToggleStateChanged(newState)
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(100))
            .background(color = MaterialTheme.colors.background.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                shape = RoundedCornerShape(100)
            )
            .padding(4.dp),
    ) {

        AnimatedContent(
            targetState = toggleState.value,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) with
                    fadeOut(animationSpec = tween(300, delayMillis = 150))
            },
        ) { state ->

            ToggleIcons(
                state = state,
                onUpdateState = { newState -> updateState(newState) }
            )
        }
    }
}

@Composable
private fun ToggleIcons(
    state: ToggleState,
    onUpdateState: (state: ToggleState) -> Unit,
) {

    Row(
        modifier = Modifier
            .wrapContentSize()
    ) {

        FlagIcon(
            modifier = Modifier.size(42.dp),
            isSelected = state is ToggleState.Reveal,
            painter = painterResource(id = R.drawable.icon_mine),
            onClicked = { onUpdateState(ToggleState.Reveal) },
        )

        Spacer(modifier = Modifier.size(4.dp))

        FlagIcon(
            modifier = Modifier.size(42.dp),
            isSelected = state is ToggleState.Flag,
            painter = rememberVectorPainter(image = Icons.Filled.Favorite),
            onClicked = { onUpdateState(ToggleState.Flag) },
        )
    }
}

@Preview(backgroundColor = 0x00FFFFFF)
@Composable
private fun Preview() {

    Toggle(
        modifier = Modifier,
        toggleState = remember { mutableStateOf(ToggleState.Flag) },
        onToggleStateChanged = {},
    )
}
