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
package com.jayasuryat.difficultyselection.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DifficultySelectionViewModel(
    private val inProgressGamesProvider: InProgressGamesProvider,
) : ViewModel() {

    private val _difficultyItems: MutableState<List<DifficultyItem>> = mutableStateOf(emptyList())
    val difficultyItems: State<List<DifficultyItem>> = _difficultyItems

    internal suspend fun loadDifficultyItems(
        difficulties: List<GameDifficulty>,
    ) {

        withContext(Dispatchers.Default) {

            val filteredInProgressDifficulties = withContext(Dispatchers.IO) {
                inProgressGamesProvider.filterGamesInProgress(difficulties)
            }

            val mapped = difficulties.map { difficulty ->

                DifficultyItem(
                    title = when (difficulty) {
                        GameDifficulty.Easy -> "Easy"
                        GameDifficulty.Medium -> "Medium"
                        GameDifficulty.Hard -> "Hard"
                    },
                    gridMessage = "${difficulty.rows} x ${difficulty.columns}",
                    difficulty = difficulty,
                    isGameInProgress = filteredInProgressDifficulties.contains(difficulty)
                )
            }

            withContext(Dispatchers.Main) { _difficultyItems.value = mapped }
        }
    }
}
