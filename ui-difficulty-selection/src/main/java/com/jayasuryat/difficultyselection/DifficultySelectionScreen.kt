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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jayasuryat.difficultyselection.composable.LevelPager
import com.jayasuryat.difficultyselection.composable.StartButton
import com.jayasuryat.difficultyselection.logic.DifficultySelectionViewModel
import com.jayasuryat.difficultyselection.logic.GameDifficulty
import com.jayasuryat.util.asImmutable

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun DifficultySelectionScreen(
    viewModel: DifficultySelectionViewModel,
    difficultiesToDisplay: List<GameDifficulty> = listOf(
        GameDifficulty.Easy,
        GameDifficulty.Medium,
        GameDifficulty.Hard,
        GameDifficulty.Extreme,
    ),
    onStartClicked: (difficulty: GameDifficulty) -> Unit,
    onResumeClicked: (difficulty: GameDifficulty) -> Unit,
    onSettingsClicked: () -> Unit,
) {

    val difficulties = viewModel.difficultyItems
    val pagerState = rememberPagerState()
    val canResume = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.loadDifficultyItems(difficultiesToDisplay)
    }

    LaunchedEffect(pagerState, difficulties.value) {

        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                val currentItem = viewModel.difficultyItems.value.getOrNull(page)
                canResume.value = currentItem?.isGameInProgress ?: false
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .fillMaxHeight(fraction = 0.33f),
            contentAlignment = Alignment.Center,
        ) {

            Text(
                text = "Minesweeper",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colors.primary,
                )
            )
        }

        LevelPager(
            pagerState = pagerState,
            difficultyLevels = difficulties.value.asImmutable(),
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
                val difficulty = difficulties.value[pagerState.currentPage]
                onStartClicked(difficulty.difficulty)
            },
        )

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            visible = canResume.value,
        ) {

            Column {

                Spacer(
                    modifier = Modifier.height(16.dp),
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
                        .clickable {
                            val difficulty = difficulties.value[pagerState.currentPage]
                            onResumeClicked(difficulty.difficulty)
                        }
                        .padding(
                            vertical = 12.dp,
                            horizontal = 40.dp,
                        ),
                    color = MaterialTheme.colors.onBackground,
                    text = "Continue",
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(1f),
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

        val padding = with(LocalDensity.current) {
            LocalWindowInsets.current.navigationBars.bottom.toDp()
        } + 32.dp

        Spacer(
            modifier = Modifier.height(padding),
        )
    }
}

// TODO: Update or remove this preview
/*
@Composable
@Preview
private fun Preview() {

    DifficultySelectionScreen(
        onDifficultySelected = {},
        onSettingsClicked = {},
    )
}
*/
