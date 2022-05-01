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
package com.jayasuryat.minesweeperjc.data.source

import com.jayasuryat.data.source.definition.GameDataSource
import com.jayasuryat.difficultyselection.logic.GameDifficulty
import com.jayasuryat.difficultyselection.logic.InProgressGamesProvider
import com.jayasuryat.minesweeperjc.data.mapper.impl.GameIdProvider

class InProgressGamesProviderImpl(
    private val gameDataSource: GameDataSource,
    private val gameIdProvider: GameIdProvider,
) : InProgressGamesProvider {

    private data class DifficultyWithId(
        val difficulty: GameDifficulty,
        val id: String,
    )

    override suspend fun filterGamesInProgress(
        difficulties: List<GameDifficulty>,
    ): List<GameDifficulty> {

        val mapped = difficulties.map { difficulty ->

            val id = gameIdProvider.getGameIdFor(
                rows = difficulty.rows,
                columns = difficulty.columns,
                totalMines = difficulty.mines,
            )

            DifficultyWithId(
                difficulty = difficulty,
                id = id,
            )
        }

        val ids = mapped.map { it.id }

        return gameDataSource.filterInProgressGameIds(ids)
            .mapNotNull { id -> mapped.find { it.id == id }?.difficulty }
    }
}
