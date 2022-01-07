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
package com.jayasuryat.minesweeperengine.controller.impl.handler

import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import kotlin.math.atan2
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

internal class RadiallySorter {

    private data class RelativeCell<T : RawCell>(
        val distance: Double,
        val angle: Double,
        val cell: T,
    )

    internal fun <T : RawCell> sortRadiallyOut(
        startingPosition: Position,
        cells: List<T>,
    ): List<T> {

        return cells.map { cell ->
            RelativeCell(
                distance = floor(startingPosition.distanceTo(cell.position)),
                angle = startingPosition.angleTo(cell.position),
                cell = cell,
            )
        }.sortedByDescending { it.angle }
            .sortedBy { it.distance }
            .map { cell -> cell.cell }
    }

    private fun Position.distanceTo(position: Position): Double {
        val rowComponent = (position.row - row).toDouble().pow(2.0)
        val columnComponent = (position.column - column).toDouble().pow(2.0)
        return sqrt(rowComponent + columnComponent)
    }

    private fun Position.angleTo(position: Position): Double {
        val deltaY = (row - position.row).toDouble()
        val deltaX = (position.column - column).toDouble()
        val result = Math.toDegrees(atan2(deltaY, deltaX))
        return if (result < 0) 360.0 + result else result
    }
}
