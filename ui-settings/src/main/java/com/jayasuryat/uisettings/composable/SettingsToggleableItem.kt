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

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jayasuryat.uisettings.R
import com.jayasuryat.uisettings.composable.param.BooleanParamProvider

@Composable
internal fun SettingsToggleableItem(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int,
    isEnabled: Boolean,
    onClicked: () -> Unit,
) {

    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100f))
            .clickable { onClicked() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                shape = RoundedCornerShape(100f),
            )
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = if (isEnabled) MaterialTheme.colors.primary else Color.Transparent)
                .border(
                    width = 1.dp,
                    color = if (isEnabled) Color.Transparent
                    else MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(100f),
                )
                .padding(12.dp),
            painter = painterResource(id = icon),
            tint = if (isEnabled) MaterialTheme.colors.background
            else MaterialTheme.colors.onBackground,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1.copy(
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Normal,
            ),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Switch(
            checked = isEnabled,
            onCheckedChange = null,
        )

        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Preview(
    name = "Settings toggleable item",
    showBackground = true,
)
@Composable
private fun Preview(
    @PreviewParameter(BooleanParamProvider::class) isEnabled: Boolean,
) {

    SettingsToggleableItem(
        modifier = Modifier.padding(all = 32.dp),
        title = "Item",
        icon = R.drawable.ic_heart,
        isEnabled = isEnabled,
        onClicked = {},
    )
}
