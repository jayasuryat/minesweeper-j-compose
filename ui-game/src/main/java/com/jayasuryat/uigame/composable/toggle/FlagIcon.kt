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
package com.jayasuryat.uigame.composable.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.jayasuryat.minesweeperui.R
import com.jayasuryat.util.ImmutableHolder
import com.jayasuryat.util.asImmutable

@Composable
internal fun FlagIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    painter: ImmutableHolder<Painter>,
    onClicked: () -> Unit,
) {

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = if (isSelected) 1f else 0.8f
                scaleY = if (isSelected) 1f else 0.8f
            }
            .alpha(if (isSelected) 1f else 0.2f)
            .clip(CircleShape)
            .background(color = MaterialTheme.colors.background)
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colors.secondary
                else MaterialTheme.colors.onBackground,
                shape = CircleShape
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { if (!isSelected) onClicked() },
            ),
        contentAlignment = Alignment.Center,
    ) {

        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .align(Alignment.Center),
            painter = painter.value,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = null,
        )
    }
}

@Preview(backgroundColor = 0x00FFFFFF)
@Composable
private fun Preview_Mine(
    @PreviewParameter(IconSelectionParamsProvider::class) isSelected: Boolean,
) {
    FlagIcon(
        modifier = Modifier.size(42.dp),
        isSelected = isSelected,
        painter = painterResource(id = R.drawable.icon_mine).asImmutable(),
        onClicked = {},
    )
}

@Preview(backgroundColor = 0x00FFFFFF)
@Composable
private fun Preview_Flag(
    @PreviewParameter(IconSelectionParamsProvider::class) isSelected: Boolean,
) {
    FlagIcon(
        modifier = Modifier.size(42.dp),
        isSelected = isSelected,
        painter = rememberVectorPainter(image = Icons.Filled.Favorite).asImmutable(),
        onClicked = {},
    )
}

internal class IconSelectionParamsProvider : PreviewParameterProvider<Boolean> {

    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
