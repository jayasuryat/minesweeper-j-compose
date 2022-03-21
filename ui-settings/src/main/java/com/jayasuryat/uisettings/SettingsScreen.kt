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
package com.jayasuryat.uisettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.jayasuryat.uisettings.composable.SettingsContent
import com.jayasuryat.uisettings.logic.SettingsChangeEvent
import com.jayasuryat.uisettings.logic.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onSettingsChanged: (event: SettingsChangeEvent) -> Unit,
    onBackPressed: () -> Unit,
) {

    val settingsState = remember { viewModel.settingsState }
    val state = settingsState.value ?: return

    val topPadding = with(LocalDensity.current) {
        LocalWindowInsets.current.statusBars.top.toDp()
    } + 32.dp

    val bottomPadding = with(LocalDensity.current) {
        LocalWindowInsets.current.navigationBars.bottom.toDp()
    } + 32.dp

    fun onEvent(
        event: SettingsChangeEvent,
    ) {
        viewModel.onSettingsChanged(event = event)
        onSettingsChanged(event)
    }

    SettingsContent(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(
                top = topPadding,
                bottom = bottomPadding,
                start = 16.dp,
                end = 16.dp,
            ),
        onBackPressed = onBackPressed,
        settingsState = state,
        onSoundToggled = { enabled ->
            onEvent(SettingsChangeEvent.OnSoundToggled(isEnabled = enabled))
        },
        onVibrationToggled = { enabled ->
            onEvent(SettingsChangeEvent.OnVibrationToggled(isEnabled = enabled))
        },
        onToggleToggled = { enabled ->
            onEvent(SettingsChangeEvent.OnShowModeToggleToggled(isEnabled = enabled))
        },
        onDefaultModeChanged = { toggleMode ->
            onEvent(SettingsChangeEvent.OnDefaultToggleModeChanged(toggleMode = toggleMode))
        },
    )
}
