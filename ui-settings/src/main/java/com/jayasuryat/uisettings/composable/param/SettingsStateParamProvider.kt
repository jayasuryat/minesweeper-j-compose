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
package com.jayasuryat.uisettings.composable.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jayasuryat.uisettings.logic.SettingsState
import com.jayasuryat.uisettings.logic.ToggleMode

internal class SettingsStateParamProvider : PreviewParameterProvider<SettingsState> {

    override val values: Sequence<SettingsState>
        get() = sequenceOf(
            SettingsState(
                isSoundEnabled = true,
                isVibrationEnabled = true,
                isToggleEnabled = true,
                defaultToggleMode = ToggleMode.Flag,
            ),
            SettingsState(
                isSoundEnabled = false,
                isVibrationEnabled = false,
                isToggleEnabled = false,
                defaultToggleMode = ToggleMode.Reveal,
            ),
            SettingsState(
                isSoundEnabled = false,
                isVibrationEnabled = false,
                isToggleEnabled = false,
                defaultToggleMode = ToggleMode.Flag,
            ),
        )
}
