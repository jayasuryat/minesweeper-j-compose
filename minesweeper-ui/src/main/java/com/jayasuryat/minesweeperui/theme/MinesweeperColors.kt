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
package com.jayasuryat.minesweeperui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
public class MinesweeperColors(
    public val minefield: Color,
    public val text: Color,
    public val mine: Color,
    public val flagIconTint: Color,
    public val mineIconTint: Color,
)

internal val DefaultColors: MinesweeperColors = MinesweeperColors(
    minefield = Color.Black,
    text = Color.White,
    mine = Color.Red,
    flagIconTint = Color.Black,
    mineIconTint = Color.White,
)
