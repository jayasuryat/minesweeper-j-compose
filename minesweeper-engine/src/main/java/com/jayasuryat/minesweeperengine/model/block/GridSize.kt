package com.jayasuryat.minesweeperengine.model.block

import androidx.compose.runtime.Stable

@Stable
public data class GridSize(
    public val rows: Int,
    public val columns: Int,
) {

    public companion object {

        public fun of(size: Int): GridSize = GridSize(columns = size, rows = size)
    }
}