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
package com.jayasuryat.uisettings.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    private val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    private val _settingsState: MutableState<SettingsState?> = mutableStateOf(null)
    internal val settingsState: State<SettingsState?> = _settingsState

    init {
        loadInitialState()
    }

    private fun loadInitialState() {

        ioScope.launch {

            val isSoundEnabled = settingsPreferences.getIsSoundEnabled()
            val isVibrationEnabled = settingsPreferences.getIsVibrationEnabled()
            val isToggleEnabled = settingsPreferences.getIsToggleEnabled()
            val toggleState = settingsPreferences.getDefaultToggleMode()

            withContext(Dispatchers.Main) {

                val state = SettingsState(
                    isSoundEnabled = isSoundEnabled,
                    isVibrationEnabled = isVibrationEnabled,
                    isToggleEnabled = isToggleEnabled,
                    defaultToggleMode = toggleState,
                )

                _settingsState.value = state
            }
        }
    }

    internal fun onSettingsChanged(
        event: SettingsChangeEvent,
    ) {

        val state = _settingsState.value ?: return

        val updatedState = when (event) {

            is SettingsChangeEvent.OnSoundToggled ->
                state.copy(isSoundEnabled = event.isEnabled)

            is SettingsChangeEvent.OnVibrationToggled ->
                state.copy(isVibrationEnabled = event.isEnabled)

            is SettingsChangeEvent.OnShowModeToggleToggled ->
                state.copy(isToggleEnabled = event.isEnabled)

            is SettingsChangeEvent.OnDefaultToggleModeChanged ->
                state.copy(defaultToggleMode = event.toggleMode)
        }

        _settingsState.value = updatedState
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}
