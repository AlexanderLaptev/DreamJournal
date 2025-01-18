package app.dreamjournal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.dreamjournal.presentation.screens.DreamJournalScreen
import app.dreamjournal.presentation.screens.SettingsScreen
import app.dreamjournal.presentation.screens.StatisticsScreen
import app.dreamjournal.presentation.screens.ToolScreen
import app.dreamjournal.presentation.screens.WikiScreen

@Composable
fun ApplicationNavigationFlow(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = ApplicationNavigation.DreamJournal.route
    ) {
        composable(
            route = ApplicationNavigation.DreamJournal.route
        ) {
            DreamJournalScreen()
        }

        composable(
            route = ApplicationNavigation.Statistics.route
        ) {
            StatisticsScreen()
        }

        composable(
            route = ApplicationNavigation.Tools.route
        ) {
            ToolScreen()
        }

        composable(
            route = ApplicationNavigation.Wiki.route
        ) {
            WikiScreen()
        }

        composable(
            route = ApplicationNavigation.Settings.route
        ) {
            SettingsScreen()
        }
    }
}