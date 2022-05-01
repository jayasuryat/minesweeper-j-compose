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
import com.jayasuryat.minesweeperengine.model.grid.Grid
import com.jayasuryat.minesweeperjc.data.mapper.definition.GridWriteMapper
import com.jayasuryat.minesweeperjc.data.mapper.impl.GameIdProvider
import com.jayasuryat.uigame.data.source.GameSaver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GameDataPersister(
    private val dataSource: GameDataSource,
    private val writeMapper: GridWriteMapper,
    private val gameIdProvider: GameIdProvider,
    dispatcher: CoroutineDispatcher,
) : GameSaver {

    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + dispatcher) }

    override fun saveGame(
        grid: Grid,
        gameState: GameSaver.GameState,
        elapsedDuration: Long,
    ) {

        scope.launch {

            when (gameState) {

                GameSaver.GameState.Started -> saveGame(
                    grid = grid,
                    elapsedDuration = elapsedDuration
                )

                GameSaver.GameState.Ended -> deleteGameForGrid(
                    grid = grid,
                )
            }
        }
    }

    private suspend fun saveGame(
        grid: Grid,
        elapsedDuration: Long,
    ) {

        val mapped = writeMapper.map(
            input = grid,
            duration = elapsedDuration
        )

        dataSource.saveGame(mapped)
    }

    private suspend fun deleteGameForGrid(
        grid: Grid,
    ) {

        val id = gameIdProvider.getGameIdFor(
            rows = grid.gridSize.rows,
            columns = grid.gridSize.columns,
            totalMines = grid.totalMines,
        )

        dataSource.deleteGameFor(id = id)
    }
}
