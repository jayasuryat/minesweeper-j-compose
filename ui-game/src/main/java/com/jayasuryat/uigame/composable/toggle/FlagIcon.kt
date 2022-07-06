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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    isSelected: State<Boolean>,
    painter: ImmutableHolder<Painter>,
    onClicked: () -> Unit,
) {

    val targetScale = remember { mutableStateOf(1f) }
    val scale = animateFloatAsState(
        targetValue = targetScale.value,
        animationSpec = tween(durationMillis = 300),
    )

    LaunchedEffect(key1 = isSelected.value) {
        targetScale.value = if (isSelected.value) 1f else 0.5f
    }

    Icon(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { if (!isSelected.value) onClicked() },
            )
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = if (isSelected.value) Color.Transparent
                else MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                shape = CircleShape,
            )
            .background(color = if (isSelected.value) MaterialTheme.colors.secondary else Color.Transparent)
            .padding(8.dp),
        painter = painter.value,
        tint = if (isSelected.value) MaterialTheme.colors.onBackground
        else MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
        contentDescription = null,
    )
}

@Preview(backgroundColor = 0x00FFFFFF)
@Composable
private fun Preview_Mine(
    @PreviewParameter(IconSelectionParamsProvider::class) isSelected: Boolean,
) {
    FlagIcon(
        modifier = Modifier.size(42.dp),
        isSelected = remember { mutableStateOf(isSelected) },
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
        isSelected = remember { mutableStateOf(isSelected) },
        painter = rememberVectorPainter(image = Icons.Filled.Favorite).asImmutable(),
        onClicked = {},
    )
}

internal class IconSelectionParamsProvider : PreviewParameterProvider<Boolean> {

    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}
