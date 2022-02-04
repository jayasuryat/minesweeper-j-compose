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
package com.jayasuryat.minesweeperui.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.jayasuryat.minesweeperui.action.CellInteractionListener
import com.jayasuryat.minesweeperui.component.ZoomableContent
import com.jayasuryat.util.LogCompositions

@Composable
public fun Minefield(
    modifier: Modifier,
    gridInfo: GridLayoutInformation,
    actionListener: CellInteractionListener,
) {

    LogCompositions(name = "Minefield")

    val savableState = rememberSaveable {
        mutableStateOf(
            ZoomPanState(
                scale = 1f,
                translationX = 0f,
                translationY = 0f,
            )
        )
    }
    val zoomPanState = remember { savableState.value }

    ZoomableContent(
        defaultState = zoomPanState,
    ) { zoomState: ZoomPanState ->

        savableState.value = zoomState

        MineGrid(
            modifier = modifier.then(Modifier.graphicsLayer {
                scaleX = zoomState.scale
                scaleY = zoomState.scale
                translationX = zoomState.translationX
                translationY = zoomState.translationY
            }),
            gridInfo = gridInfo,
            actionListener = actionListener,
        )
    }
}
