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
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.cell.CELL_ICON_PADDING_PERCENT
import com.jayasuryat.minesweeperui.theme.msColors
import com.jayasuryat.util.LogCompositions

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FlaggedCell(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongPressed: () -> Unit,
) {

    LogCompositions(name = "FlaggedCell")

    val haptic = LocalHapticFeedback.current

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.primary)
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    // TODO: 20/01/22 Handle haptics properly
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                },
                onLongClick = {
                    // TODO: 20/01/22 Handle haptics properly
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPressed()
                },
            ),
        contentAlignment = Alignment.Center,
    ) {

        Icon(
            modifier = Modifier.fillMaxSize(1 - CELL_ICON_PADDING_PERCENT),
            imageVector = Icons.Filled.Favorite,
            tint = MaterialTheme.msColors.flagIconTint,
            contentDescription = null,
        )
    }
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {

    FlaggedCell(
        modifier = Modifier.fillMaxSize(),
        onClick = {},
        onLongPressed = {},
    )
}
