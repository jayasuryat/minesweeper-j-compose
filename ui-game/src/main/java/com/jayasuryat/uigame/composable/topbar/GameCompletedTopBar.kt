/*
 * Copyright 2021 Jaya Surya Thotapalli
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
package com.jayasuryat.uigame.composable.topbar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun GameCompletedTopBar(
    modifier: Modifier = Modifier,
    elapsedDuration: Long,
    onRestartClicked: () -> Unit,
) {

    Row(
        modifier = modifier
    ) {

        TextChip(
            text = "Done in ${formatTime(elapsedDuration)}",
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically),
            contentColor = Color.Green
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Filled.Refresh,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(38.dp)
                .align(alignment = Alignment.CenterVertically)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape,
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) { onRestartClicked() }
                .padding(8.dp),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun Preview() {

    GameCompletedTopBar(
        modifier = Modifier.wrapContentSize(),
        elapsedDuration = ((1 * 60) + (33)) * 1000L,
        onRestartClicked = {},
    )
}
