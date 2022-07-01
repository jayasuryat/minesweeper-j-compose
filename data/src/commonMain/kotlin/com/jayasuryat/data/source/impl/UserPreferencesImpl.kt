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
package com.jayasuryat.data.source.impl

import com.jayasuryat.data.source.definition.UserPreferences
import com.jayasuryat.data.store.DataStore

internal class UserPreferencesImpl(
    private val store: DataStore,
) : UserPreferences {

    override suspend fun getIsSoundEnabled(): Boolean =
        store.getBoolean(PREF_SOUND_ENABLED, true)

    override suspend fun setIsSoundEnabled(enabled: Boolean): Unit =
        store.putBoolean(PREF_SOUND_ENABLED, enabled)

    override suspend fun getIsVibrationEnabled(): Boolean =
        store.getBoolean(PREF_VIBRATION_ENABLED, true)

    override suspend fun setIsVibrationEnabled(enabled: Boolean): Unit =
        store.putBoolean(PREF_VIBRATION_ENABLED, enabled)

    override suspend fun getShouldShowToggle(): Boolean =
        store.getBoolean(PREF_SHOW_TOGGLE, true)

    override suspend fun setShouldShowToggle(show: Boolean): Unit =
        store.putBoolean(PREF_SHOW_TOGGLE, show)

    override suspend fun getDefaultToggleMode(): String? = store.getString(PREF_DEFAULT_TOGGLE)
    override suspend fun setDefaultToggleMode(mode: String): Unit =
        store.putString(PREF_DEFAULT_TOGGLE, mode)

    private companion object {

        private const val PREF_SOUND_ENABLED: String = "pref:sound"
        private const val PREF_VIBRATION_ENABLED: String = "pref:vibration"
        private const val PREF_SHOW_TOGGLE: String = "pref:showToggle"
        private const val PREF_DEFAULT_TOGGLE: String = "pref:defaultToggle"
    }
}
