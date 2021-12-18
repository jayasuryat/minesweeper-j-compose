/*
 * Copyright 2021 Jaya Surya Thotapalli
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
package com.jayasuryat.difficultyselection.logic

sealed class GameDifficulty {

    abstract val rows: Int
    abstract val columns: Int
    abstract val mines: Int

    internal object Easy : GameDifficulty() {
        override val rows: Int = 10
        override val columns: Int = 10
        override val mines: Int = 10
    }

    internal object Medium : GameDifficulty() {
        override val rows: Int = 15
        override val columns: Int = 15
        override val mines: Int = 23
    }

    internal object Hard : GameDifficulty() {
        override val rows: Int = 25
        override val columns: Int = 15
        override val mines: Int = 37
    }

    internal object Extreme : GameDifficulty() {
        override val rows: Int = 40
        override val columns: Int = 20
        override val mines: Int = 100
    }
}
