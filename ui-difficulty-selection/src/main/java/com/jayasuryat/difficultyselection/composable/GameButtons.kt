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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun GameButtons(
    modifier: Modifier = Modifier,
    canResume: State<Boolean>,
    onStartClicked: () -> Unit,
    onResumeClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        RoundedButton(
            modifier = Modifier
                .wrapContentSize()
                .alpha(if (canResume.value) 0.7f else 1f),
            text = "Start",
            onClick = onStartClicked,
        )

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            visible = canResume.value,
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                RoundedButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = "Resume",
                    onClick = onResumeClicked,
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(1f),
        )


        RoundedButton(
            modifier = Modifier
                .wrapContentSize(),
            text = "Settings",
            onClick = onSettingsClicked,
        )

        val padding = with(LocalDensity.current) {
            LocalWindowInsets.current.navigationBars.bottom.toDp()
        } + 32.dp

        Spacer(
            modifier = Modifier.height(padding),
        )
    }
}

@Preview(
    name = "Game buttons",
    showBackground = true,
)
@Composable
private fun Preview() {
    GameButtons(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(1 / 3f),
        canResume = remember { mutableStateOf(true) },
        onStartClicked = {},
        onResumeClicked = {},
        onSettingsClicked = {},
    )
}
