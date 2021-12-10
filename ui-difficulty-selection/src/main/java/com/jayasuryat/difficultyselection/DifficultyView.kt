package com.jayasuryat.difficultyselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jayasuryat.difficultyselection.logic.DifficultyParamProvider
import com.jayasuryat.difficultyselection.logic.GameDifficulty

@Composable
internal fun DifficultyView(
    modifier: Modifier = Modifier,
    difficulty: GameDifficulty,
) {

    Column(
        modifier = modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            text = difficulty.getTitle(),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 10 x 10
        Text(
            modifier = Modifier
                .wrapContentSize()
                .wrapContentSize(),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            text = difficulty.getRowColDef(),
        )

        Row {

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .wrapContentSize()
                    .align(alignment = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                text = difficulty.mines.toString(),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.error)
                    .padding(4.dp),
                painter = painterResource(id = R.drawable.icon_mine),
                tint = MaterialTheme.colors.onError,
                contentDescription = null,
            )
        }
    }
}

@Stable
private fun GameDifficulty.getTitle(): String {

    return when (this) {
        is GameDifficulty.Easy -> "Easy"
        is GameDifficulty.Medium -> "Medium"
        is GameDifficulty.Hard -> "Hard"
    }
}

@Stable
private fun GameDifficulty.getRowColDef(): String {
    return "$rows x $columns"
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(DifficultyParamProvider::class) difficulty: GameDifficulty,
) {

    DifficultyView(difficulty = difficulty)
}