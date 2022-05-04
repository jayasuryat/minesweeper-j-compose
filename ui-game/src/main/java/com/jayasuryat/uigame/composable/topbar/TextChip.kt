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
package com.jayasuryat.uigame.composable.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun TextChip(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colors.onBackground,
    backgroundColor: Color = MaterialTheme.colors.background,
    strokeColor: Color = MaterialTheme.colors.onBackground,
) {

    Text(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(color = backgroundColor)
            .border(
                width = 2.dp,
                color = strokeColor,
                shape = RoundedCornerShape(100),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        text = text,
        style = MaterialTheme.typography.body1.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        ),
    )
}

@Preview
@Composable
private fun Preview() {

    TextChip(
        modifier = Modifier.wrapContentSize(),
        text = "I am Groot",
        textColor = Color.White,
        backgroundColor = Color.Black,
        strokeColor = Color.White,
    )
}
