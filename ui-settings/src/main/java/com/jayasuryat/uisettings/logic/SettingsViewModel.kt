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
import com.jayasuryat.uisettings.composable.ToggleMode
import kotlinx.coroutines.*

class SettingsViewModel(
    private val settingsUpdateCallback: SettingsUpdateCallback,
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    private val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    private val _soundEnabled: MutableState<Boolean> = mutableStateOf(true)
    internal val soundEnabled: State<Boolean> = _soundEnabled

    private val _vibrationEnabled: MutableState<Boolean> = mutableStateOf(true)
    internal val vibrationEnabled: MutableState<Boolean> = _vibrationEnabled

    private val _toggleEnabled: MutableState<Boolean> = mutableStateOf(true)
    internal val toggleEnabled: MutableState<Boolean> = _toggleEnabled

    private val _toggleMode: MutableState<ToggleMode> = mutableStateOf(ToggleMode.Reveal)
    internal val toggleMode: MutableState<ToggleMode> = _toggleMode

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

                _soundEnabled.value = isSoundEnabled
                _vibrationEnabled.value = isVibrationEnabled
                _toggleEnabled.value = isToggleEnabled
                _toggleMode.value = toggleState
            }
        }
    }

    internal fun onSoundToggled(enabled: Boolean) {
        ioScope.launch {
            settingsUpdateCallback.onSoundToggled(enabled = enabled)
            _soundEnabled.value = settingsPreferences.getIsSoundEnabled()
        }
    }

    internal fun onVibrationToggled(enabled: Boolean) {
        ioScope.launch {
            settingsUpdateCallback.onVibrationToggled(enabled = enabled)
            _vibrationEnabled.value = settingsPreferences.getIsVibrationEnabled()
        }
    }

    internal fun onToggleToggled(enabled: Boolean) {
        ioScope.launch {
            settingsUpdateCallback.onModeToggled(enabled = enabled)
            _toggleEnabled.value = settingsPreferences.getIsToggleEnabled()
        }
    }

    internal fun onDefaultToggleModeChanged(mode: ToggleMode) {
        ioScope.launch {
            settingsUpdateCallback.onDefaultModeToggled(mode = mode)
            _toggleMode.value = settingsPreferences.getDefaultToggleMode()
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}
