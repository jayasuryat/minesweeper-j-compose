package com.jayasuryat.data.sqldelight

import com.squareup.sqldelight.db.SqlDriver

public expect class DriverFactory {

    public fun createDriver(): SqlDriver
}

internal val DriverFactory.DatabaseName: String
    get() = "minesweeper.db"