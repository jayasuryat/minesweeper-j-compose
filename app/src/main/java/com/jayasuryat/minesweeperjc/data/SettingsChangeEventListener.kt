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
package com.jayasuryat.minesweeperjc.data

import com.jayasuryat.data.settings.sources.definitions.UserPreferences
import com.jayasuryat.uigame.feedback.sound.SoundStatusProvider
import com.jayasuryat.uigame.feedback.vibration.VibrationStatusProvider
import com.jayasuryat.uisettings.logic.SettingsChangeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsChangeEventListener(
    private val userPreferences: UserPreferences,
) : (SettingsChangeEvent) -> Unit,
    SoundStatusProvider,
    VibrationStatusProvider {

    private val ioScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    private var isSoundEnabled: Boolean = false
    private var isVibrationEnabled: Boolean = false

    init {
        ioScope.launch { loadInitialState() }
    }

    private suspend fun loadInitialState() {
        isSoundEnabled = userPreferences.getIsSoundEnabled()
        isVibrationEnabled = userPreferences.getIsVibrationEnabled()
    }

    override fun invoke(event: SettingsChangeEvent) {
        ioScope.launch { handleEvent(event = event) }
    }

    override fun isSoundEnabled(): Boolean = isSoundEnabled
    override fun isVibrationEnabled(): Boolean = isVibrationEnabled

    private suspend fun handleEvent(event: SettingsChangeEvent) {

        when (event) {

            is SettingsChangeEvent.OnSoundToggled -> {
                isSoundEnabled = event.isEnabled
                userPreferences.setIsSoundEnabled(enabled = event.isEnabled)
            }

            is SettingsChangeEvent.OnVibrationToggled -> {
                isVibrationEnabled = event.isEnabled
                userPreferences.setIsVibrationEnabled(enabled = event.isEnabled)
            }

            is SettingsChangeEvent.OnShowModeToggleToggled ->
                userPreferences.setShouldShowToggle(show = event.isEnabled)

            is SettingsChangeEvent.OnDefaultToggleModeChanged ->
                userPreferences.setDefaultToggleMode(mode = event.toggleMode.name)
        }
    }

    companion object {

        @Suppress("ObjectPropertyName")
        @Volatile
        private var _instance: SettingsChangeEventListener? = null

        // TODO: Migrate to a proper DI solution
        fun getInstance(
            userPreferences: UserPreferences,
        ): SettingsChangeEventListener {

            _instance?.let { return it }

            synchronized(this@Companion) {

                _instance?.let { return it }

                val instance = SettingsChangeEventListener(
                    userPreferences = userPreferences
                )
                _instance = instance
                return instance
            }
        }
    }
}
