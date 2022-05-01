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

import com.jayasuryat.data.model.Grid
import com.jayasuryat.data.source.definition.GameDataSource
import com.jayasuryat.minesweeperjc.data.mapper.impl.GameIdProvider

internal class SavedGameFetcher(
    private val dataSource: GameDataSource,
    private val gameIdProvider: GameIdProvider,
) {

    suspend fun getSavedGameFor(
        rows: Int,
        columns: Int,
        totalMines: Int,
    ): Grid? {

        val id = gameIdProvider.getGameIdFor(
            rows = rows,
            columns = columns,
            totalMines = totalMines,
        )

        return dataSource.getSavedGameFor(id = id)
    }
}
