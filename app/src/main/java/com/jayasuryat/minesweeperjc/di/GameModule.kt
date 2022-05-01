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

import android.content.Context
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.gridgenerator.GridGenerator
import com.jayasuryat.minesweeperjc.data.initialgrid.EmptyInitialGridProviderImpl
import com.jayasuryat.minesweeperjc.data.initialgrid.InitialGridProviderImpl
import com.jayasuryat.minesweeperjc.data.mapper.definition.GridReadMapper
import com.jayasuryat.minesweeperjc.data.source.SavedGameFetcher
import com.jayasuryat.uigame.GameViewModel
import com.jayasuryat.uigame.data.source.GameDataSource
import com.jayasuryat.uigame.feedback.sound.MusicManager
import com.jayasuryat.uigame.feedback.sound.SoundStatusProvider
import com.jayasuryat.uigame.feedback.vibration.VibrationManager
import com.jayasuryat.uigame.feedback.vibration.VibrationStatusProvider
import com.jayasuryat.uigame.logic.EmptyGridGenerator
import com.jayasuryat.uigame.logic.InitialGridProvider
import com.jayasuryat.uigame.logic.model.GameConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val IGP_RESUMABLE: String = "igp_resumable"
internal const val IGP_NEW_GAME: String = "igp_newGame"

@Suppress("RemoveExplicitTypeArguments")
internal val gameModule = module {

    single<MusicManager> {
        MusicManager(
            context = get<Context>().applicationContext,
            soundStatusProvider = get<SoundStatusProvider>()
        )
    }

    single<VibrationManager> {
        VibrationManager(
            context = get<Context>().applicationContext,
            vibrationStatusProvider = get<VibrationStatusProvider>()
        )
    }

    factory<InitialGridProvider>(named(IGP_NEW_GAME)) {
        EmptyInitialGridProviderImpl(
            emptyGridGenerator = get<EmptyGridGenerator>(),
            backingGridGenerator = get<GridGenerator>(),
        )
    }

    factory<InitialGridProvider>(named(IGP_RESUMABLE)) {
        InitialGridProviderImpl(
            savedGameFetcher = get<SavedGameFetcher>(),
            gridReadMapper = get<GridReadMapper>(),
            newGameGridProvider = get<InitialGridProvider>(named(IGP_NEW_GAME)),
        )
    }

    viewModel<GameViewModel> { params ->
        GameViewModel(
            initialGridProvider = params.get<InitialGridProvider>(),
            gameConfiguration = params.get<GameConfiguration>(),
            minefieldController = get<MinefieldController>(),
            soundManager = get<MusicManager>(),
            vibrationManager = get<VibrationManager>(),
            dataSource = get<GameDataSource>(),
        )
    }
}
