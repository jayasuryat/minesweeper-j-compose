/*
 * Copyright 2022 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
