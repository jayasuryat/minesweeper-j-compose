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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jayasuryat.uisettings.R
import com.jayasuryat.uisettings.composable.param.BooleanParamProvider
import com.jayasuryat.uisettings.logic.ToggleMode

@Composable
internal fun DefaultToggleMode(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    toggleState: ToggleMode,
    onModeChanged: (ToggleMode) -> Unit,
) {

    Column(
        modifier = modifier
            .alpha(alpha = if (isEnabled) 1f else 0.3f),
    ) {

        Text(
            text = "Default click behavior",
            style = MaterialTheme.typography.h6,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            ToggleableItem(
                modifier = Modifier.weight(1f),
                title = "Reveal",
                icon = R.drawable.ic_mine,
                enabled = isEnabled,
                isSelected = toggleState == ToggleMode.Reveal,
                onModeChanged = { onModeChanged(ToggleMode.Reveal) },
            )

            Spacer(modifier = Modifier.width(16.dp))

            ToggleableItem(
                modifier = Modifier.weight(1f),
                title = "Flag",
                icon = R.drawable.ic_heart,
                enabled = isEnabled,
                isSelected = toggleState == ToggleMode.Flag,
                onModeChanged = { onModeChanged(ToggleMode.Flag) },
            )
        }
    }
}

@Preview(
    name = "Default toggle mode",
    showBackground = true,
)
@Composable
private fun Preview(
    @PreviewParameter(BooleanParamProvider::class) isEnabled: Boolean,
) {

    DefaultToggleMode(
        modifier = Modifier.padding(all = 32.dp),
        isEnabled = isEnabled,
        toggleState = ToggleMode.Flag,
        onModeChanged = {},
    )
}
