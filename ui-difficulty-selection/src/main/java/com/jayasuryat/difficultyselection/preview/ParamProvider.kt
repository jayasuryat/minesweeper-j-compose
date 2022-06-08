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
package com.jayasuryat.difficultyselection.composable

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jayasuryat.difficultyselection.logic.DifficultyItem
import com.jayasuryat.difficultyselection.logic.GameDifficulty

internal class GameDifficultyListParamProvider : PreviewParameterProvider<List<GameDifficulty>> {

    override val values: Sequence<List<GameDifficulty>>
        get() = sequenceOf(
            listOf(
                GameDifficulty.Easy,
                GameDifficulty.Medium,
                GameDifficulty.Hard,
            )
        )
}

internal class GameDifficultyParamProvider : PreviewParameterProvider<GameDifficulty> {

    override val values: Sequence<GameDifficulty>
        get() = sequenceOf(
            GameDifficulty.Easy,
            GameDifficulty.Medium,
            GameDifficulty.Hard,
        )
}

internal class DifficultyItemParamProvider : PreviewParameterProvider<DifficultyItem> {

    override val values: Sequence<DifficultyItem>
        get() = sequenceOf(
            DifficultyItem(
                title = "Easy",
                gridMessage = "10 x 10",
                difficulty = GameDifficulty.Easy,
                isGameInProgress = true,
            ),
            DifficultyItem(
                title = "Medium",
                gridMessage = "15 x 15",
                difficulty = GameDifficulty.Medium,
                isGameInProgress = false,
            ),
        )
}
