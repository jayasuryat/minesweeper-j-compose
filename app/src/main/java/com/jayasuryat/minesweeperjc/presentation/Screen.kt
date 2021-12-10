package com.jayasuryat.minesweeperjc.presentation

sealed interface Screen {

    fun getRoute(): String

    object DifficultySelection : Screen {
        override fun getRoute(): String = "difficultySelection"
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
        ): String = "minefield/${rows}/${columns}/${mines}"
    }
}