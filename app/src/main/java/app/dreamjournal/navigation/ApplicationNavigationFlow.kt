package app.dreamjournal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.dreamjournal.ui.dream.DreamJournalScreen
import app.dreamjournal.ui.InfoScreen
import app.dreamjournal.ui.dream.StatisticsScreen
import app.dreamjournal.ui.ToolsScreen

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
            ToolsScreen()
        }

        composable(
            route = ApplicationNavigation.Info.route
        ) {
            InfoScreen()
        }
    }
}
