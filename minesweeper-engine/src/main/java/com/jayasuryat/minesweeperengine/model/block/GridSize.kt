package com.jayasuryat.minesweeperengine.model.block


internal data class GridSize(
    val rows: Int,
    val columns: Int,
) {

    internal companion object {

        internal fun of(size: Int): GridSize = GridSize(columns = size, rows = size)
    }
}