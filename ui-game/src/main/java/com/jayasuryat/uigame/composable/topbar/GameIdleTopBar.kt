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

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun GameIdleTopBar(
    modifier: Modifier = Modifier,
) {

    TextChip(
        text = "Tap a cell to start",
        modifier = modifier,
        contentColor = MaterialTheme.colors.background,
        textColor = MaterialTheme.colors.onBackground,
        strokeColor = MaterialTheme.colors.onBackground,
    )
}

@Preview
@Composable
private fun Preview() {

    GameIdleTopBar(
        modifier = Modifier.wrapContentSize(),
    )
}
