package com.jayasuryat.data.sqldelight

import com.jayasuryat.data.MinesweeperDatabase

public class DatabaseCreator {

    public fun createDatabase(
        driverFactory: DriverFactory,
    ): MinesweeperDatabase {
        val driver = driverFactory.createDriver()
        return MinesweeperDatabase(driver)
    }
}