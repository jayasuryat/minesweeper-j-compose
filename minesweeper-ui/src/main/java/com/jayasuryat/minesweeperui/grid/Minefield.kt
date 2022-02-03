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
import androidx.compose.ui.Modifier
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

    ZoomableContent { zoomModifier: Modifier ->

        MineGrid(
            modifier = modifier.then(zoomModifier),
            gridInfo = gridInfo,
            actionListener = actionListener,
        )
    }
}
