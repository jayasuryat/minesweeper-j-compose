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
import com.jayasuryat.data.settings.sources.definitions.UserPreferences
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.impl.GameController
import com.jayasuryat.minesweeperengine.gridgenerator.GridGenerator
import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperjc.data.GameDataSourceImpl
import com.jayasuryat.minesweeperjc.data.ToggleStateChangeListener
import com.jayasuryat.uigame.GameViewModel
import com.jayasuryat.uigame.data.GameDataSource
import com.jayasuryat.uigame.feedback.sound.MusicManager
import com.jayasuryat.uigame.feedback.sound.SoundStatusProvider
import com.jayasuryat.uigame.feedback.vibration.VibrationManager
import com.jayasuryat.uigame.feedback.vibration.VibrationStatusProvider
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.uigame.logic.ToggleState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
internal val gameModule = module {

    factory<(ToggleState) -> Unit> {
        ToggleStateChangeListener(
            userPreferences = get<UserPreferences>()
        )
    }

    factory<GameDataSource> {
        GameDataSourceImpl(
            userPreferences = get<UserPreferences>()
        )
    }

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

    viewModel<GameViewModel> {
        GameViewModel(
            gameConfiguration = get<GameConfiguration>(),
            gridGenerator = get<GridGenerator>(),
            minefieldController = get<MinefieldController>(),
            soundManager = get<MusicManager>(),
            vibrationManager = get<VibrationManager>(),
            dataSource = get<GameDataSource>(),
        )
    }
}

@Suppress("RemoveExplicitTypeArguments")
internal val gameEngineModule = module {

    single<GridGenerator> {
        MineGridGenerator()
    }

    single<MinefieldController> {
        GameController.getDefault()
    }
}
