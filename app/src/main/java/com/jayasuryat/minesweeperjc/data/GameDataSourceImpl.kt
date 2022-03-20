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
import com.jayasuryat.uigame.data.GameDataSource
import com.jayasuryat.uigame.logic.ToggleState
import com.jayasuryat.uisettings.logic.ToggleMode

class GameDataSourceImpl(
    private val userPreferences: UserPreferences,
) : GameDataSource {

    override suspend fun getToggleState(): ToggleState {
        val mode = userPreferences.getDefaultToggleMode()
            .takeIf { !it.isNullOrEmpty() } ?: return ToggleState.Reveal
        return ToggleMode.valueOf(mode).toToggleState()
    }
}
