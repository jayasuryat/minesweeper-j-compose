package com.jayasuryat.data.sqldelight

import com.jayasuryat.data.MinesweeperDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

public actual class DriverFactory {

    public actual fun createDriver(): SqlDriver {

        return NativeSqliteDriver(
            schema = MinesweeperDatabase.Schema,
            name = DatabaseName,
        )
    }
}