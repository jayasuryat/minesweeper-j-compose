package com.jayasuryat.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
@ReadOnlyComposable
public fun Float.dp(): Dp = with(LocalDensity.current) { Dp(this@dp / density) }

@Composable
@ReadOnlyComposable
public fun Dp.floatValue(): Float = with(LocalDensity.current) { this@floatValue.value * density }

@Composable
@ReadOnlyComposable
public fun Dp.sp(): TextUnit = with(LocalDensity.current) { this@sp.toSp() }