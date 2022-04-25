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
package com.jayasuryat.data.game.mapper.definition

import com.jayasuryat.data.model.Cell

internal interface GridMapper : GridToStringMapper, StringToGridMapper

internal fun interface GridToStringMapper {
    fun mapToString(grid: List<List<Cell>>): String
}

internal fun interface StringToGridMapper {
    fun mapToGrid(grid: String): List<List<Cell>>
}
