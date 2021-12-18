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
package com.jayasuryat.uigame.composable.topbar.started

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.uigame.composable.topbar.TextChip
import com.jayasuryat.uigame.composable.topbar.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
internal fun TickerChip(
    modifier: Modifier = Modifier,
    startTime: Long,
) {

    val elapsedDuration = remember { mutableStateOf(0L) }

    LaunchedEffect(key1 = startTime) {
        while (this.isActive) {
            delay(1000)
            elapsedDuration.value = System.currentTimeMillis() - startTime
        }
    }

    TextChip(
        modifier = modifier,
        text = formatTime(elapsedDuration.value),
        contentColor = MaterialTheme.colors.background,
        textColor = MaterialTheme.colors.onBackground,
        strokeColor = MaterialTheme.colors.onBackground,
    )
}

@Preview
@Composable
private fun Preview() {

    TickerChip(
        startTime = System.currentTimeMillis()
    )
}
