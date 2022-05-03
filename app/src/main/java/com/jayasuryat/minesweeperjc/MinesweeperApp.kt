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
package com.jayasuryat.minesweeperjc

import android.app.Application
import com.jayasuryat.data.di.dataModule
import com.jayasuryat.minesweeperjc.di.*
import jp.wasabeef.takt.Seat
import jp.wasabeef.takt.Takt
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class MinesweeperApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //attachTakt()
        initKoin()
    }

    private fun attachTakt() {

        Takt.stock(this)
            .showOverlaySetting(BuildConfig.DEBUG)
            .seat(Seat.BOTTOM_RIGHT)
            .interval(250)
            .color(android.graphics.Color.WHITE)
            .size(32f)
            .alpha(1f)
    }

    private fun initKoin() {

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MinesweeperApp)
            modules(appDataModule)
            modules(dataModule)
            modules(difficultySelectionModule)
            modules(settingsModule)
            modules(gameModule)
            modules(gameEngineModule)
        }
    }
}
