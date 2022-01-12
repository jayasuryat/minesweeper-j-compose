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
package com.jayasuryat.minesweeperjc.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jayasuryat.difficultyselection.DifficultySelectionScreen
import com.jayasuryat.minesweeperjc.util.ViewModelFactory
import com.jayasuryat.uigame.GameScreen
import com.jayasuryat.uigame.GameViewModel
import com.jayasuryat.uigame.logic.GameConfiguration
import com.jayasuryat.util.LogCompositions
import java.util.*

@Composable
fun MineSweeper() {

    LogCompositions(name = "MineSweeper")

    ProvideWindowInsets {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            MinesweeperApp()
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun MinesweeperApp() {

    LogCompositions(name = "MinesweeperApp")

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.DifficultySelection.getRoute(),
    ) {

        composable(
            enterTransition = { _, _ ->
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                )
            },
            exitTransition = { _, _ ->
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                )
            },
            route = Screen.DifficultySelection.getRoute(),
        ) {

            DifficultySelectionScreen(
                onDifficultySelected = { difficulty ->
                    val route = Screen.Minefield.getNavigableRoute(
                        rows = difficulty.rows,
                        columns = difficulty.columns,
                        mines = difficulty.mines,
                    )
                    navController.navigate(route = route)
                }
            )
        }

        composable(
            enterTransition = { initial, _ ->
                val isGameRoute = initial.destination.route == Screen.Minefield.getRoute()
                if (isGameRoute) slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                ) else slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                )
            },
            exitTransition = { _, target ->
                val isGameRoute = target.destination.route == Screen.Minefield.getRoute()
                if (isGameRoute) slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                ) else slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(PAGE_NAV_DURATION),
                )
            },
            route = Screen.Minefield.getRoute(),
            arguments = listOf(
                navArgument(Screen.Minefield.ROWS) { type = NavType.IntType },
                navArgument(Screen.Minefield.COLUMNS) { type = NavType.IntType },
                navArgument(Screen.Minefield.MINES) { type = NavType.IntType },
            )
        ) {

            val rows = it.arguments?.getInt(Screen.Minefield.ROWS)
            val columns = it.arguments?.getInt(Screen.Minefield.COLUMNS)
            val mines = it.arguments?.getInt(Screen.Minefield.MINES)

            require(rows != null && columns != null && mines != null) {
                "Insufficient data to navigate to Minefield"
            }

            val gameConfiguration = GameConfiguration(
                gameId = UUID.randomUUID().toString(),
                rows = rows,
                columns = columns,
                mines = mines,
            )

            val context = LocalContext.current.applicationContext

            GameScreen(
                viewModel = viewModel(
                    factory = ViewModelFactory {
                        GameViewModel(
                            context = context,
                            gameConfiguration = gameConfiguration,
                        )
                    }
                ),
                onRestartClicked = {
                    val route = Screen.Minefield.getNavigableRoute(
                        rows = rows,
                        columns = columns,
                        mines = mines,
                    )
                    navController.navigate(route = route) {
                        popUpTo(Screen.Minefield.getRoute()) { inclusive = true }
                    }
                }
            )
        }
    }
}

private const val PAGE_NAV_DURATION: Int = 900
