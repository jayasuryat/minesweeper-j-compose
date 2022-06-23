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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperui.R
import com.jayasuryat.uigame.data.model.ToggleState
import com.jayasuryat.util.LogCompositions
import com.jayasuryat.util.asImmutable

@Composable
internal fun Toggle(
    modifier: Modifier,
    toggleState: State<ToggleState>,
    onToggleStateChanged: (newState: ToggleState) -> Unit,
) {

    LogCompositions(name = "Toggle")

    val isInRevealMode = remember { derivedStateOf { toggleState.value == ToggleState.Reveal } }
    val isInFlagMode = remember { derivedStateOf { toggleState.value == ToggleState.Flag } }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(100))
            .background(color = MaterialTheme.colors.background)
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(100)
            )
            .padding(4.dp),
    ) {

        ToggleIcons(
            isInRevealMode = isInRevealMode,
            isInFlagMode = isInFlagMode,
            onUpdateState = onToggleStateChanged,
        )
    }
}

@Composable
private fun ToggleIcons(
    isInRevealMode: State<Boolean>,
    isInFlagMode: State<Boolean>,
    onUpdateState: (state: ToggleState) -> Unit,
) {

    Row(
        modifier = Modifier
            .wrapContentSize()
    ) {

        FlagIcon(
            modifier = Modifier
                .size(48.dp),
            isSelected = isInRevealMode,
            painter = painterResource(id = R.drawable.icon_mine).asImmutable(),
            onClicked = { onUpdateState(ToggleState.Reveal) },
        )

        FlagIcon(
            modifier = Modifier
                .size(48.dp),
            isSelected = isInFlagMode,
            painter = rememberVectorPainter(image = Icons.Filled.Favorite).asImmutable(),
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
