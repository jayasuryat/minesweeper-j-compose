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
package com.jayasuryat.data.game.mapper.impl

import com.jayasuryat.data.game.mapper.definition.GridMapper
import com.jayasuryat.data.model.Cell
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class GridMapperImpl(
    private val json: Json,
) : GridMapper {

    override fun mapToString(
        grid: List<List<Cell>>,
    ): String = json.encodeToString(grid)

    override fun mapToGrid(
        grid: String,
    ): List<List<Cell>> = json.decodeFromString(grid)
}
