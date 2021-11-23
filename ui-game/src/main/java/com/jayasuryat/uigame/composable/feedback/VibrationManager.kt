package com.jayasuryat.uigame.composable.feedback

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

internal object VibrationManager {

    fun shortVibrationNow(context: Context) = vibrationNow(150, context)
    fun mediumVibrationNow(context: Context) = vibrationNow(500, context)
    fun strongVibrationNow(context: Context) = vibrationNow(1000, context)

    private fun vibrationNow(
        mills: Long,
        context: Context,
    ) {

        val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager?)
                ?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator?
        } ?: return

        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(mills,
                VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(mills)
        }
    }
}