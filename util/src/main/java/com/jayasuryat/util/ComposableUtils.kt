package com.jayasuryat.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
public fun Float.dp(): Dp = with(LocalDensity.current) { Dp(this@dp / density) }

@Composable
public fun Dp.floatValue(): Float = with(LocalDensity.current) { this@floatValue.value * density }

@Composable
public fun Dp.sp(): TextUnit = with(LocalDensity.current) { this@sp.toSp() }