package com.jayasuryat.uigame.feedback

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

internal class VibrationManager(
    private val context: Context,
) {

    private val vibrator: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager)
                .defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun pop() = vibrationNow(mills = 1, amplitude = 12)
    fun shortVibrationNow() = vibrationNow(mills = 200, amplitude = 100)
    fun mediumVibrationNow() = vibrationNow(mills = 500, amplitude = 100)

    private fun vibrationNow(
        mills: Long,
        amplitude: Int,
    ) {

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(mills, amplitude))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(mills)
        }
    }
}