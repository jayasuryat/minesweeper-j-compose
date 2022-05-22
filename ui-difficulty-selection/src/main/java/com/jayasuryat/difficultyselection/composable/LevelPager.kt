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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jayasuryat.difficultyselection.logic.DifficultyItem
import com.jayasuryat.util.ImmutableHolder
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun LevelPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    difficultyLevels: ImmutableHolder<List<DifficultyItem>>,
) {

    val levels = difficultyLevels.value

    Row(
        modifier = modifier,
    ) {

        val scope = rememberCoroutineScope()

        // Left icon
        Icon(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                ) {
                    val nextPage = pagerState.currentPage - 1
                    if (nextPage < 0) return@clickable
                    scope.launch { pagerState.animateScrollToPage(nextPage) }
                }
                .padding(horizontal = 16.dp)
                .width(48.dp)
                .fillMaxHeight()
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowLeft,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null,
        )

        // Pager
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .align(alignment = Alignment.CenterVertically),
        ) {

            HorizontalPager(
                count = levels.size,
                state = pagerState,
            ) { page ->
                // Our page content
                DifficultyView(
                    modifier = Modifier.fillMaxSize(),
                    difficulty = levels[page],
                )
            }
        }

        // Right icon
        Icon(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                ) {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage >= pagerState.pageCount) return@clickable
                    scope.launch { pagerState.animateScrollToPage(nextPage) }
                }
                .padding(horizontal = 16.dp)
                .width(48.dp)
                .fillMaxHeight()
                .align(alignment = Alignment.CenterVertically),
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null,
        )
    }
}
