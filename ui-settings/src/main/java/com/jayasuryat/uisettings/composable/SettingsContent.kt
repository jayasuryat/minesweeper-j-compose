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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jayasuryat.uisettings.R
import com.jayasuryat.uisettings.composable.param.SettingsStateParamProvider
import com.jayasuryat.uisettings.logic.SettingsState
import com.jayasuryat.uisettings.logic.ToggleMode

@Composable
internal fun SettingsContent(
    modifier: Modifier = Modifier,
    settingsState: SettingsState,
    onBackPressed: () -> Unit,
    onSoundToggled: (enabled: Boolean) -> Unit,
    onVibrationToggled: (enabled: Boolean) -> Unit,
    onToggleToggled: (enabled: Boolean) -> Unit,
    onDefaultModeChanged: (mode: ToggleMode) -> Unit,
) {

    Column(
        modifier = modifier,
    ) {

        // Back icon
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(shape = CircleShape)
                .clickable { onBackPressed() }
                .padding(all = 8.dp),
        )

        // Title
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            ),
            text = "Settings",
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.ExtraLight,
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Settings(
            settingsState = settingsState,
            onSoundToggled = onSoundToggled,
            onVibrationToggled = onVibrationToggled,
            onToggleToggled = onToggleToggled,
            onDefaultModeChanged = onDefaultModeChanged,
        )
    }
}

@Preview(
    name = "Settings content",
    showBackground = true,
    widthDp = 512,
)
@Composable
private fun Preview(
    @PreviewParameter(SettingsStateParamProvider::class) settingsState: SettingsState,
) {

    SettingsContent(
        modifier = Modifier.padding(all = 24.dp),
        onBackPressed = { },
        settingsState = settingsState,
        onSoundToggled = { },
        onVibrationToggled = { },
        onToggleToggled = { },
        onDefaultModeChanged = {},
    )
}
