package com.jayasuryat.minesweeperui.composable.event

import android.util.Log

internal val NoOpActionListener: MinefieldActionsListener
    get() = MinefieldActionsListener { event ->
        Log.d("MinefieldAction", "On action received : $event")
    }