package com.jayasuryat.data.settings.sources.definitions

public interface UserPreferences {

    public suspend fun getIsSoundEnabled(): Boolean
    public suspend fun setIsSoundEnabled(enabled: Boolean)

    public suspend fun getIsVibrationEnabled(): Boolean
    public suspend fun setIsVibrationEnabled(enabled: Boolean)
}