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
package com.jayasuryat.data.source.impl

import com.jayasuryat.data.MinesweeperDatabase
import com.jayasuryat.data.source.definition.GameDataSource
import com.jayasuryat.data.model.Cell
import com.jayasuryat.data.model.Grid
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class GameDataSourceImpl(
    private val json: Json,
    private val database: MinesweeperDatabase,
) : GameDataSource {

    override suspend fun saveGame(
        grid: Grid,
    ) {

        database.gridQueries.insertGrid(
            id = grid.id,
            startTime = grid.startTime,
            endTime = grid.endTime,
            grid = grid.grid.mapToString(),
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
            grid = grid.grid.mapToListOfCells(),
        )
    }

    private fun List<List<Cell>>.mapToString(): String = json.encodeToString(this)

    private fun String.mapToListOfCells(): List<List<Cell>> = json.decodeFromString(this)
}
