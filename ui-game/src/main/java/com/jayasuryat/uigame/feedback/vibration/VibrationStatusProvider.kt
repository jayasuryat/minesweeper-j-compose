package com.jayasuryat.uigame.feedback.vibration

fun interface VibrationStatusProvider {
    fun isVibrationEnabled(): Boolean
}