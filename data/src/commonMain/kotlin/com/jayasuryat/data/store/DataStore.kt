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
package com.jayasuryat.data.store

import com.russhwolf.settings.Settings
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("RedundantSuspendModifier", "unused")
internal class DataStore constructor(
    private val settings: Settings,
    @PublishedApi internal val json: Json,
) {

    suspend fun putString(key: String, value: String): Unit = settings.putString(key, value)
    suspend fun getString(key: String): String? = settings.getString(key)
        .takeIf { it.isNotBlank() }

    suspend fun putInt(key: String, value: Int): Unit = settings.putInt(key, value)
    suspend fun getInt(key: String, default: Int = 0): Int = settings.getInt(key, default)

    suspend fun putLong(key: String, value: Long): Unit = settings.putLong(key, value)
    suspend fun getLong(key: String, defValue: Long = -1): Long? {
        val value = settings.getLong(key, -1)
        return if (value == defValue) null else value
    }

    suspend fun putBoolean(key: String, value: Boolean): Unit =
        settings.putBoolean(key, value)

    suspend fun getBoolean(
        key: String,
        default: Boolean = false,
    ): Boolean = settings.getBoolean(key, default)

    suspend inline fun <reified T> putObject(key: String, value: T): Unit =
        putString(key, json.encodeToString(value))

    suspend inline fun <reified T> getObject(key: String): T? {
        val data = getString(key)
        if (data.isNullOrEmpty()) return null
        return json.decodeFromString(data)
    }

    suspend fun remove(vararg keys: String) {
        keys.forEach {
            settings.remove(it)
        }
    }

    suspend fun clear() {
        settings.clear()
    }
}
