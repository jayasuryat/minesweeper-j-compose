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
package com.jayasuryat.minesweeperjc.di

import com.jayasuryat.data.source.definition.UserPreferences
import com.jayasuryat.minesweeperjc.data.mapper.definition.GridReadMapper
import com.jayasuryat.minesweeperjc.data.mapper.definition.GridWriteMapper
import com.jayasuryat.minesweeperjc.data.mapper.impl.GameIdProvider
import com.jayasuryat.minesweeperjc.data.mapper.impl.GridReadMapperImpl
import com.jayasuryat.minesweeperjc.data.mapper.impl.GridWriteMapperImpl
import com.jayasuryat.minesweeperjc.data.source.GameDataPersister
import com.jayasuryat.minesweeperjc.data.source.GameDataSourceImpl
import com.jayasuryat.minesweeperjc.data.source.SavedGameFetcher
import com.jayasuryat.minesweeperjc.data.source.SettingsPreferencesImpl
import com.jayasuryat.uigame.data.source.GameDataSource
import com.jayasuryat.uigame.data.source.GameSaver
import com.jayasuryat.uisettings.logic.SettingsPreferences
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
internal val appDataModule = module {

    factory<GameDataSource> {
        GameDataSourceImpl(
            userPreferences = get<UserPreferences>(),
            gameSaver = get<GameSaver>(),
        )
    }

    factory<SettingsPreferences> {
        SettingsPreferencesImpl(
            userPreferences = get<UserPreferences>()
        )
    }

    factory<GameIdProvider> {
        GameIdProvider()
    }

    factory<GridWriteMapper> {
        GridWriteMapperImpl(
            gameIdProvider = get<GameIdProvider>()
        )
    }

    factory<GridReadMapper> {
        GridReadMapperImpl()
    }

    @OptIn(DelicateCoroutinesApi::class)
    factory<GameDataPersister> {
        GameDataPersister(
            dataSource = get<com.jayasuryat.data.source.definition.GameDataSource>(),
            gameIdProvider = get<GameIdProvider>(),
            readMapper = get<GridReadMapper>(),
            writeMapper = get<GridWriteMapper>(),
            dispatcher = Dispatchers.IO,
        )
    }

    factory<GameSaver> {
        get<GameDataPersister>()
    }

    factory<SavedGameFetcher> {
        SavedGameFetcher(
            dataSource = get<com.jayasuryat.data.source.definition.GameDataSource>(),
            gameIdProvider = get<GameIdProvider>(),
        )
    }
}
