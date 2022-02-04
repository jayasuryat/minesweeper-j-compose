package com.jayasuryat.minesweeperui.grid

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
internal data class ZoomPanState(
    val scale: Float,
    val translationX: Float,
    val translationY: Float,
) : Parcelable