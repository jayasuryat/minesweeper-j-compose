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

import com.jayasuryat.data.settings.sources.definitions.UserPreferences
import com.jayasuryat.minesweeperjc.data.SettingsChangeEventListener
import com.jayasuryat.minesweeperjc.data.SettingsPreferencesImpl
import com.jayasuryat.uigame.feedback.sound.SoundStatusProvider
import com.jayasuryat.uigame.feedback.vibration.VibrationStatusProvider
import com.jayasuryat.uisettings.logic.SettingsChangeListener
import com.jayasuryat.uisettings.logic.SettingsPreferences
import com.jayasuryat.uisettings.logic.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
internal val settingsModule = module {

    single<SettingsChangeEventListener> {
        SettingsChangeEventListener(
            userPreferences = get<UserPreferences>()
        )
    }

    factory<SettingsChangeListener> {
        get<SettingsChangeEventListener>()
    }

    factory<SoundStatusProvider> {
        get<SettingsChangeEventListener>()
    }

    factory<VibrationStatusProvider> {
        get<SettingsChangeEventListener>()
    }

    factory<SettingsPreferences> {
        SettingsPreferencesImpl(
            userPreferences = get<UserPreferences>()
        )
    }

    viewModel {
        SettingsViewModel(
            settingsPreferences = get<SettingsPreferences>()
        )
    }
}
