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
package com.jayasuryat.data.game.sources.impl

import com.jayasuryat.data.MinesweeperDatabase
import com.jayasuryat.data.game.mapper.definition.GridToStringMapper
import com.jayasuryat.data.game.mapper.definition.StringToGridMapper
import com.jayasuryat.data.game.sources.definition.GameDataSource
import com.jayasuryat.data.model.Grid

internal class GameDataSourceImpl(
    private val database: MinesweeperDatabase,
    private val gridMapper: GridToStringMapper,
    private val stringMapper: StringToGridMapper,
) : GameDataSource {

    override suspend fun saveGame(
        grid: Grid,
    ) {

        database.gridQueries.insertGrid(
            id = grid.id,
            startTime = grid.startTime,
            endTime = grid.endTime,
            grid = gridMapper.mapToString(grid.grid),
        )
    }

    override suspend fun getSavedGameFor(
        id: String,
    ): Grid? {

        val grid = database.gridQueries
            .getGridForId(id = id)
            .executeAsOneOrNull() ?: return null

        return Grid(
            id = id,
            startTime = grid.startTime,
            endTime = grid.endTime,
            grid = stringMapper.mapToGrid(grid.grid),
        )
    }
}
