package com.jayasuryat.uigame

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