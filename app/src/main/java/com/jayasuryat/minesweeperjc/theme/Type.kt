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
package com.jayasuryat.minesweeperjc.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.jayasuryat.minesweeperjc.R

private val NunitoFontFamily = FontFamily(
    Font(R.font.nunito_extra_light, FontWeight.W100),
    Font(R.font.nunito_light, FontWeight.W300),
    Font(R.font.nunito_regular, FontWeight.W400),
    Font(R.font.nunito_medium, FontWeight.W500),
    Font(R.font.nunito_semi_bold, FontWeight.W600),
    Font(R.font.nunito_bold, FontWeight.W700),
    Font(R.font.nunito_extra_bold, FontWeight.W800),
)

val Typography = Typography(
    defaultFontFamily = NunitoFontFamily,
)
