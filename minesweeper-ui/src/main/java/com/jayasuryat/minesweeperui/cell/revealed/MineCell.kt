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
package com.jayasuryat.minesweeperui.cell.revealed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.R
import com.jayasuryat.minesweeperui.theme.msColors
import com.jayasuryat.util.LogCompositions

@Composable
internal fun MineCell(
    modifier: Modifier = Modifier,
) {

    LogCompositions(name = "MineCell")

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = MaterialTheme.msColors.mine),
        contentAlignment = Alignment.Center,
    ) {

        Icon(
            modifier = Modifier.fillMaxSize(0.70f),
            painter = painterResource(id = R.drawable.icon_mine),
            tint = MaterialTheme.msColors.mineIconTint,
            contentDescription = null,
        )
    }
}

@Preview(heightDp = 600, widthDp = 600)
@Preview(heightDp = 60, widthDp = 60)
@Composable
private fun Preview() {

    MineCell(
        modifier = Modifier.fillMaxSize(),
    )
}
