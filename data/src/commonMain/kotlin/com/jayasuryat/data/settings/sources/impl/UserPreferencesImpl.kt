package com.jayasuryat.data.settings.sources.impl

import com.jayasuryat.data.settings.sources.definitions.UserPreferences
import com.jayasuryat.data.store.DataStore

public class UserPreferencesImpl(
    private val store: DataStore,
) : UserPreferences {

    override suspend fun getIsSoundEnabled(): Boolean = store.getBoolean(PREF_SOUND_ENABLED)
    override suspend fun setIsSoundEnabled(enabled: Boolean): Unit =
        store.putBoolean(PREF_SOUND_ENABLED, enabled)

    override suspend fun getIsVibrationEnabled(): Boolean = store.getBoolean(PREF_VIBRATION_ENABLED)
    override suspend fun setIsVibrationEnabled(enabled: Boolean): Unit =
        store.putBoolean(PREF_VIBRATION_ENABLED, enabled)

    private companion object {

        private const val PREF_SOUND_ENABLED: String = "pref:sound"
        private const val PREF_VIBRATION_ENABLED: String = "pref:vibration"
    }
}