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
}