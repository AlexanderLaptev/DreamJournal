package app.dreamjournal.ui.journal.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import app.dreamjournal.ui.DreamJournalNavigation


fun NavGraphBuilder.dreamDetailsDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.DreamDetailsRoute> {
        val dreamId = it.toRoute<DreamJournalNavigation.DreamDetailsRoute>().dreamId
        DreamDetailsScreen(
            dreamId = dreamId,
            onBack = onBack,
        )
    }
}

fun NavController.navigateToDreamDetails(dreamId: Long) {
    navigate(DreamJournalNavigation.DreamDetailsRoute(dreamId)) {
        launchSingleTop = true
    }
}
