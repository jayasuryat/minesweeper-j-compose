package com.jayasuryat.minesweeperui.composable.cell.revealed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayasuryat.minesweeperui.R
import com.jayasuryat.minesweeperui.composable.cell.CELL_PADDING_PERCENT
import com.jayasuryat.minesweeperui.composable.theme.msColors
import com.jayasuryat.util.LogCompositions

@Composable
internal fun MineCell(
    modifier: Modifier = Modifier,
) {

    LogCompositions(name = "MineCell")

    BoxWithConstraints(modifier = modifier
        .aspectRatio(1f)
        .clipToBounds()
        .background(color = MaterialTheme.msColors.minefield)
    ) {

        val minSize = minOf(maxWidth, maxHeight)
        val padding = minSize * CELL_PADDING_PERCENT

        Icon(
            modifier = modifier
                .padding(all = padding)
                .clip(CircleShape)
                .background(color = MaterialTheme.msColors.mine)
                .padding(all = padding),
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