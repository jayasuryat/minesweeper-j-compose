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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.jayasuryat.difficultyselection.composable.DifficultyView
import com.jayasuryat.difficultyselection.composable.GameButtons
import com.jayasuryat.difficultyselection.composable.GameTitle
import com.jayasuryat.difficultyselection.logic.DifficultySelectionViewModel
import com.jayasuryat.difficultyselection.logic.GameDifficulty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()

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

    val paddingTop = with(LocalDensity.current) {
        val statusBar = LocalWindowInsets.current.statusBars.top.toDp()
        val cutOut = LocalWindowInsets.current.displayCutout.top.toDp()
        maxOf(statusBar, cutOut) + 32.dp
    }

    fun scrollRight() {
        coroutineScope.launch {
            val currentPage = pagerState.currentPage
            if (currentPage < pagerState.pageCount)
                pagerState.animateScrollToPage(page = currentPage + 1)
        }
    }

    fun scrollLeft() {
        coroutineScope.launch {
            val currentPage = pagerState.currentPage
            if (currentPage > 0)
                pagerState.animateScrollToPage(page = currentPage - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(top = paddingTop),
    ) {

        GameTitle(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.2f)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
        ) {

            DifficultyView(
                modifier = Modifier.fillMaxSize(),
                pagerState = pagerState,
                difficultyItems = difficulties.value,
            )

            PagerButtons(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                onLeftClicked = ::scrollLeft,
                onRightClicked = ::scrollRight,
            )
        }

        GameButtons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            canResume = canResume,
            onStartClicked = {
                val item = difficulties.value[pagerState.currentPage].difficulty
                onStartClicked(item)
            },
            onResumeClicked = {
                val item = difficulties.value[pagerState.currentPage].difficulty
                onResumeClicked(item)
            },
            onSettingsClicked = onSettingsClicked,
        )
    }
}

@Composable
fun PagerButtons(
    modifier: Modifier = Modifier,
    onLeftClicked: () -> Unit,
    onRightClicked: () -> Unit,
) {

    Row(
        modifier = modifier,
    ) {

        // Left icon
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape,
                )
                .clickable { onLeftClicked() }
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowLeft,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.weight(1f))

        // Right icon
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape,
                )
                .clickable { onRightClicked() }
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null,
        )
    }
}
