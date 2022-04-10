package com.jayasuryat.data.sqldelight

import android.content.Context
import com.jayasuryat.data.MinesweeperDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

public actual class DriverFactory(
    private val context: Context,
) {

    public actual fun createDriver(): SqlDriver {

        return AndroidSqliteDriver(
            schema = MinesweeperDatabase.Schema,
            context = context,
            name = DatabaseName,
        )
    }
}