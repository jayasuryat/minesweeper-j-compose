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

import com.jayasuryat.uisettings.composable.ToggleMode
import com.jayasuryat.uisettings.logic.SettingsUpdateCallback

class SettingsUpdateCallbackImpl : SettingsUpdateCallback {

    override suspend fun onSoundToggled(enabled: Boolean) {
        InMemoryDataSource.isSoundEnabled = enabled
    }

    override suspend fun onVibrationToggled(enabled: Boolean) {
        InMemoryDataSource.isVibrationEnabled = enabled
    }

    override suspend fun onModeToggled(enabled: Boolean) {
        InMemoryDataSource.isToggleEnabled = enabled
    }

    override suspend fun onDefaultModeToggled(mode: ToggleMode) {
        InMemoryDataSource.toggleMode = mode
    }
}