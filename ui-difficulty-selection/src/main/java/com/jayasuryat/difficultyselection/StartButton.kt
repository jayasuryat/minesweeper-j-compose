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
package com.jayasuryat.difficultyselection

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets

@Composable
internal fun StartButton(
    modifier: Modifier = Modifier,
    onStartClicked: () -> Unit,
) {

    val padding = with(LocalDensity.current) {
        LocalWindowInsets.current.navigationBars.bottom.toDp()
    } + 16.dp

    Text(
        modifier = modifier
            .padding(bottom = padding)
            .wrapContentSize()
            .clip(RoundedCornerShape(100f))
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(100f),
            )
            .clickable { onStartClicked() }
            .padding(
                vertical = 12.dp,
                horizontal = 40.dp,
            ),
        color = MaterialTheme.colors.onBackground,
        text = "Start",
    )
}
