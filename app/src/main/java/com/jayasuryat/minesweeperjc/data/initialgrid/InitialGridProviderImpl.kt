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
package com.jayasuryat.minesweeperjc.data.initialgrid

import com.jayasuryat.minesweeperjc.data.mapper.definition.GridReadMapper
import com.jayasuryat.minesweeperjc.data.source.SavedGameFetcher
import com.jayasuryat.uigame.logic.InitialGridProvider
import com.jayasuryat.uigame.logic.model.InitialGrid

internal class InitialGridProviderImpl(
    private val savedGameFetcher: SavedGameFetcher,
    private val gridReadMapper: GridReadMapper,
    private val newGameGridProvider: InitialGridProvider,
) : InitialGridProvider {

    override suspend fun getInitialGridFor(
        rows: Int,
        columns: Int,
        totalMines: Int,
    ): InitialGrid {

        val inProgressGrid = savedGameFetcher.getSavedGameFor(
            rows = rows,
            columns = columns,
            totalMines = totalMines,
        )

        if (inProgressGrid != null) {

            val mapped = gridReadMapper.map(inProgressGrid)

            return InitialGrid.InProgressGrid(
                elapsedDuration = inProgressGrid.duration,
                grid = mapped,
            )
        }

        return newGameGridProvider.getInitialGridFor(
            rows = rows,
            columns = columns,
            totalMines = totalMines,
        )
    }
}
