package app.dreamjournal.ui.statistics

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.statisticsDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.StatisticsRoute> {
        StatisticsScreen(onBack = onBack)
    }
}

fun NavController.navigateToStatistics() {
    navigate(DreamJournalNavigation.StatisticsRoute) {
        launchSingleTop = true
    }
}
