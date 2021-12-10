package com.jayasuryat.minesweeperjc.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.jayasuryat.difficultyselection.DifficultySelectionScreen
import com.jayasuryat.uigame.GameScreen
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
private fun MinesweeperApp() {

    LogCompositions(name = "MinesweeperApp")

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.DifficultySelection.getRoute(),
    ) {

        composable(
            route = Screen.DifficultySelection.getRoute(),
        ) {

            DifficultySelectionScreen(onDifficultySelected = { difficulty ->
                val route = Screen.Minefield.getNavigableRoute(
                    rows = difficulty.rows,
                    columns = difficulty.columns,
                    mines = difficulty.mines,
                )
                navController.navigate(route = route)
            })
        }

        composable(
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

            GameScreen(
                gameConfiguration = gameConfiguration,
                onRestartClicked = {
                    val route = Screen.DifficultySelection.getRoute()
                    navController.navigate(route = route)
                }
            )
        }
    }
}
