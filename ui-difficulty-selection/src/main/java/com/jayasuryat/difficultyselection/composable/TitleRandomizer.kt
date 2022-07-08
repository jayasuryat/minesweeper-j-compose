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
package com.jayasuryat.difficultyselection.composable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class TitleRandomizer(
    private val title: String,
) {

    private val originalTitle: List<TitleChar> = title.mapIndexed { index, char ->
        TitleChar(
            id = "$index$char",
            char = char,
            isPositionCorrect = true,
        )
    }

    fun getRandomizedTitle(
        coroutineScope: CoroutineScope,
    ): Flow<List<TitleChar>> {

        val flow: MutableStateFlow<List<TitleChar>> = MutableStateFlow(originalTitle)

        coroutineScope.launch {

            delay(2000)

            flow.value = getTotallyRandomTitle()

            while (coroutineScope.isActive) {

                delay(600)

                val currentValue = flow.value
                val arePositionsIncorrect = currentValue.any { !it.isPositionCorrect }

                val newValue = if (arePositionsIncorrect) currentValue.fixOneCharacter()
                else {
                    // If all of the characters are displayed correctly, holding that order for a bit
                    delay(2000)
                    getTotallyRandomTitle()
                }

                flow.value = newValue
            }
        }

        return flow
    }

    private fun getTotallyRandomTitle(): List<TitleChar> {

        // Keeping the first character in it's original place
        return originalTitle.takeLast(title.length - 1)
            .shuffled()
            .toMutableList()
            .apply { add(0, originalTitle.first()) }
            .updatePositionCorrectness()
    }

    private fun List<TitleChar>.fixOneCharacter(): List<TitleChar> {

        val randomIncorrectChar = this
            .filter { !it.isPositionCorrect }
            .random()

        val currentPosition = this.indexOf(randomIncorrectChar)
        val correctPosition = originalTitle.indexOfFirst { it.char == randomIncorrectChar.char }

        return this.toMutableList().apply {
            val swapValue = this[correctPosition]
            this[correctPosition] = randomIncorrectChar
            this[currentPosition] = swapValue
        }.updatePositionCorrectness()
    }

    private fun List<TitleChar>.updatePositionCorrectness(): List<TitleChar> {
        return this.toMutableList().mapIndexed { index, titleChar ->
            val isCorrect = originalTitle[index].char == titleChar.char
            titleChar.copy(isPositionCorrect = isCorrect)
        }
    }

    data class TitleChar(
        val id: String,
        val char: Char,
        val isPositionCorrect: Boolean,
    )
}
