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
package com.jayasuryat.uigame.logic

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface GameState {

    object Idle : GameState

    class GameStarted(
        val startTime: Long,
    ) : GameState {

        internal companion object {

            fun now(): GameStarted {
                return GameStarted(startTime = System.currentTimeMillis())
            }
        }
    }

    sealed interface GameEnded : GameState {

        val endTime: Long

        data class GameOver(
            override val endTime: Long,
        ) : GameEnded {

            internal companion object {

                fun now(): GameOver {
                    return GameOver(endTime = System.currentTimeMillis())
                }
            }
        }

        data class GameCompleted(
            override val endTime: Long,
            val elapsedDuration: Long,
        ) : GameEnded {

            internal companion object {

                fun now(
                    startTime: Long,
                ): GameCompleted {

                    val endTime = System.currentTimeMillis()

                    return GameCompleted(
                        endTime = endTime,
                        elapsedDuration = endTime - startTime,
                    )
                }
            }
        }
    }
}
