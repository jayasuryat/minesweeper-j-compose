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
package com.jayasuryat.difficultyselection.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collect

private const val APP_NAME: String = "Minesweeper"

@Composable
internal fun GameTitle(
    modifier: Modifier = Modifier,
) {

    val titleRandomizer = remember { TitleRandomizer(title = APP_NAME) }
    val title: MutableState<List<TitleRandomizer.TitleChar>> = remember { mutableStateOf(listOf()) }

    LaunchedEffect(key1 = true) {
        titleRandomizer.getRandomizedTitle(coroutineScope = this)
            .collect { title.value = it }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        AnimatedTitle(
            modifier = Modifier
                .wrapContentSize(),
            title = title,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AnimatedTitle(
    modifier: Modifier = Modifier,
    title: State<List<TitleRandomizer.TitleChar>>,
) {

    LazyRow(modifier = modifier) {

        items(
            items = title.value,
            key = { char -> char.id },
        ) { char ->

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .animateItemPlacement(),
                text = char.char.toString(),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (char.isPositionCorrect) MaterialTheme.colors.primary
                    else MaterialTheme.colors.onBackground,
                )
            )
        }
    }
}

@Preview(
    name = "Game title",
    showBackground = true,
)
@Composable
private fun Preview() {
    GameTitle()
}
