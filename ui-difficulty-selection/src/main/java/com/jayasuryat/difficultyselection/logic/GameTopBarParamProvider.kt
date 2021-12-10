package com.jayasuryat.difficultyselection.logic

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class DifficultyParamProvider : PreviewParameterProvider<GameDifficulty> {

    override val values: Sequence<GameDifficulty>
        get() = sequenceOf(
            GameDifficulty.Easy,
            GameDifficulty.Medium,
            GameDifficulty.Hard,
        )
}