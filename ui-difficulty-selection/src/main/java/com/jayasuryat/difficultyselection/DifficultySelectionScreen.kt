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
package com.jayasuryat.difficultyselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jayasuryat.difficultyselection.logic.GameDifficulty

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DifficultySelectionScreen(
    difficulties: List<GameDifficulty> = listOf(
        GameDifficulty.Easy,
        GameDifficulty.Medium,
        GameDifficulty.Hard,
        GameDifficulty.Extreme,
    ),
    onDifficultySelected: (difficulty: GameDifficulty) -> Unit,
    onSettingsClicked: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {

        val pagerState = rememberPagerState()

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxHeight(fraction = 0.33f),
            contentAlignment = Alignment.Center,
        ) {

            Text(
                text = "Minesweeper",
                fontSize = 40.sp,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center,
            )
        }

        LevelPager(
            pagerState = pagerState,
            difficultyLevels = difficulties,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.33f),
        )

        Spacer(
            modifier = Modifier.height(64.dp),
        )

        StartButton(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            onStartClicked = {
                val difficulty = difficulties[pagerState.currentPage]
                onDifficultySelected(difficulty)
            },
        )

        Spacer(
            modifier = Modifier.height(64.dp),
        )

        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .wrapContentSize()
                .clip(RoundedCornerShape(100f))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(100f),
                )
                .clickable { onSettingsClicked() }
                .padding(
                    vertical = 12.dp,
                    horizontal = 40.dp,
                ),
            color = MaterialTheme.colors.onBackground,
            text = "Settings",
        )
    }
}

@Composable
@Preview
private fun Preview() {

    DifficultySelectionScreen(
        onDifficultySelected = {},
        onSettingsClicked = {},
    )
}
