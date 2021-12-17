package com.jayasuryat.minesweeperjc

import android.app.Application
import jp.wasabeef.takt.Seat
import jp.wasabeef.takt.Takt

@Suppress("unused")
class MinesweeperApp : Application() {

    override fun onCreate() {
        super.onCreate()
        attachTakt()
    }

    private fun attachTakt() {

        Takt.stock(this)
            .seat(Seat.BOTTOM_RIGHT)
            .interval(250)
            .color(android.graphics.Color.WHITE)
            .size(32f)
            .alpha(1f)
    }
}
