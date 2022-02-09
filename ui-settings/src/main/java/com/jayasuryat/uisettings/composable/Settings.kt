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
package com.jayasuryat.uisettings.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayasuryat.uisettings.R

@Composable
internal fun Settings(
    modifier: Modifier = Modifier,
    soundEnabled: Boolean,
    vibrationEnabled: Boolean,
    toggleEnabled: Boolean,
    toggleMode: ToggleMode,
    onSoundToggled: (enabled: Boolean) -> Unit,
    onVibrationToggled: (enabled: Boolean) -> Unit,
    onToggleToggled: (enabled: Boolean) -> Unit,
    onDefaultModeChanged: (mode: ToggleMode) -> Unit,
) {

    Column(
        modifier = modifier,
    ) {

        // Sound item
        SettingsToggleableItem(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = "Sound",
            icon = R.drawable.ic_sound,
            isEnabled = soundEnabled,
            onClicked = { onSoundToggled(!soundEnabled) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Vibration item
        SettingsToggleableItem(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = "Vibration",
            icon = R.drawable.ic_vibration,
            isEnabled = vibrationEnabled,
            onClicked = { onVibrationToggled(!vibrationEnabled) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Show mode toggle
        SettingsToggleableItem(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = "Show mode toggle",
            icon = R.drawable.ic_toggle,
            isEnabled = toggleEnabled,
            onClicked = { onToggleToggled(!toggleEnabled) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Default toggle mode
        DefaultToggleMode(
            modifier = Modifier.padding(horizontal = 8.dp),
            isEnabled = !toggleEnabled,
            toggleState = toggleMode,
            onModeChanged = { onDefaultModeChanged(it) }
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 512
)
@Composable
private fun Preview() {

    Settings(
        modifier = Modifier.padding(all = 32.dp),
        soundEnabled = true,
        vibrationEnabled = true,
        toggleEnabled = true,
        toggleMode = ToggleMode.Reveal,
        onSoundToggled = {},
        onVibrationToggled = {},
        onToggleToggled = {},
        onDefaultModeChanged = {},
    )
}
