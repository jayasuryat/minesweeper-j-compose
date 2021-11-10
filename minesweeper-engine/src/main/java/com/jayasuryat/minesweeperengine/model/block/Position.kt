package com.jayasuryat.minesweeperengine.model.block


public data class Position(
    public val row: Int,
    public val column: Int,
) {

    public companion object {

        public fun zero(): Position = Position(row = 0, column = 0)
    }
}