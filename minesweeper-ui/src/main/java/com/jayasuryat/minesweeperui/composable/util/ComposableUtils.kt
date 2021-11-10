package com.jayasuryat.minesweeperui.composable.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
internal fun Float.dp(): Dp = with(LocalDensity.current) { Dp(this@dp / density) }

@Composable
internal fun Dp.floatValue(): Float = with(LocalDensity.current) { this@floatValue.value * density }

@Composable
internal fun Dp.sp(): TextUnit = with(LocalDensity.current) { this@sp.toSp() }