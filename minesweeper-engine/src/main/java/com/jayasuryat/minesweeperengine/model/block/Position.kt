package com.jayasuryat.minesweeperengine.model.block


internal data class Position(
    val row: Int,
    val column: Int,
) {

    internal companion object {

        internal fun zero(): Position = Position(row = 0, column = 0)
    }
}