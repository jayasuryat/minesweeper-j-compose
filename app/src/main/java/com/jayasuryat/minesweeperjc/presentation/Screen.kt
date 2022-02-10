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
package com.jayasuryat.minesweeperjc.presentation

sealed interface Screen {

    fun getRoute(): String

    object DifficultySelection : Screen {
        override fun getRoute(): String = "difficultySelection"
    }

    object Settings : Screen {
        override fun getRoute(): String = "settings"
    }

    object Minefield : Screen {

        internal const val ROWS: String = "rows"
        internal const val COLUMNS: String = "columns"
        internal const val MINES: String = "mines"

        override fun getRoute(): String = "minefield/{$ROWS}/{$COLUMNS}/{$MINES}"

        fun getNavigableRoute(
            rows: Int,
            columns: Int,
            mines: Int,
        ): String = "minefield/$rows/$columns/$mines"
    }
}
