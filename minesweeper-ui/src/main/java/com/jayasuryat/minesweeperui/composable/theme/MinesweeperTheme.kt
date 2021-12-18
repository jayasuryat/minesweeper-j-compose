/*
 * Copyright 2021 Jaya Surya Thotapalli
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
package com.jayasuryat.minesweeperui.composable.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
public fun MinesweeperTheme(
    colors: MinesweeperColors = DefaultColors,
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(LocalColors provides colors) {
        content()
    }
}

internal val LocalColors = staticCompositionLocalOf { DefaultColors }

@Suppress("unused")
internal val MaterialTheme.msColors: MinesweeperColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current
