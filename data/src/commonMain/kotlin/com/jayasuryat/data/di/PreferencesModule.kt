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
package com.jayasuryat.data.di

import com.jayasuryat.data.settings.sources.definitions.UserPreferences
import com.jayasuryat.data.settings.sources.impl.UserPreferencesImpl
import com.jayasuryat.data.store.DataStore
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
internal val preferencesModule: Module = module {

    single<Settings> { Settings() }

    single<DataStore> {
        DataStore(
            settings = get<Settings>(),
            json = get<Json>(),
        )
    }

    factory<UserPreferences> {
        UserPreferencesImpl(
            store = get<DataStore>(),
        )
    }
}
