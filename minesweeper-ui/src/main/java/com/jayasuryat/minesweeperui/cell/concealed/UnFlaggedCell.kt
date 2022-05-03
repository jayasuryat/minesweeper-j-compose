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
package com.jayasuryat.minesweeperui.cell.concealed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperui.cell.CELL_RING_PERCENT
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UnFlaggedCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongPressed: () -> Unit,
) {

    LogCompositions(name = "UnFlaggedCell")

    val haptic = LocalHapticFeedback.current

    val borderWidth = remember { mutableStateOf(1.dp) }
    val localDensity = LocalDensity.current

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.primary)
            .border(
                width = borderWidth.value,
                color = MaterialTheme.colors.onBackground,
                shape = CircleShape,
            )
            .onGloballyPositioned {
                val size = minOf(it.size.height, it.size.width) * CELL_RING_PERCENT
                val width = with(localDensity) { size.toDp() }
                borderWidth.value = width
            }
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPressed()
                },
            ),
    )
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {

    UnFlaggedCell(
        modifier = Modifier.fillMaxSize(),
        onClick = {},
        onLongPressed = {},
    )
}
