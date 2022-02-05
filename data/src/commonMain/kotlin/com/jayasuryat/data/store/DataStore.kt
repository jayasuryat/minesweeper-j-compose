package com.jayasuryat.data.store

import com.russhwolf.settings.Settings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("RedundantSuspendModifier", "unused")
public class DataStore constructor(
    private val settings: Settings,
    @PublishedApi internal val json: Json,
) {

    public suspend fun putString(key: String, value: String): Unit = settings.putString(key, value)
    public suspend fun getString(key: String): String? = settings.getString(key)
        .takeIf { it.isNotBlank() }

    public suspend fun putInt(key: String, value: Int): Unit = settings.putInt(key, value)
    public suspend fun getInt(key: String, default: Int = 0): Int = settings.getInt(key, default)

    public suspend fun putLong(key: String, value: Long): Unit = settings.putLong(key, value)
    public suspend fun getLong(key: String, defValue: Long = -1): Long? {
        val value = settings.getLong(key, -1)
        return if (value == defValue) null else value
    }

    public suspend fun putBoolean(key: String, value: Boolean): Unit =
        settings.putBoolean(key, value)

    public suspend fun getBoolean(key: String): Boolean = settings.getBoolean(key, false)

    public suspend inline fun <reified T> putObject(key: String, value: T): Unit =
        putString(key, json.encodeToString(value))

    public suspend inline fun <reified T> getObject(key: String): T? {
        val data = getString(key)
        if (data.isNullOrEmpty()) return null
        return json.decodeFromString(data)
    }

    public suspend fun remove(vararg keys: String) {
        keys.forEach {
            settings.remove(it)
        }
    }

    public suspend fun clear() {
        settings.clear()
    }
}