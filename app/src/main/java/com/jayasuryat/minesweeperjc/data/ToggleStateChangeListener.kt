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
import com.jayasuryat.uigame.logic.ToggleState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ToggleStateChangeListener(
    private val userPreferences: UserPreferences,
) : (ToggleState) -> Unit {

    private val ioScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

    override fun invoke(event: ToggleState) {
        ioScope.launch { handleEvent(event = event) }
    }

    private suspend fun handleEvent(event: ToggleState) {
        userPreferences.setDefaultToggleMode(event.toToggleMode().name)
    }
}
