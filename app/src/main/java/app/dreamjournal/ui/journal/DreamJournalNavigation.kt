package app.dreamjournal.ui.journal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.dreamJournalDestination(
    navController: NavController,
    onDreamClick: (DreamWithTags) -> Unit,
) {
    composable<DreamJournalNavigation.DreamJournalRoute> {
        DreamJournalScreen(
            navController = navController,
            onDreamClick = onDreamClick,
        )
    }
}

fun NavController.navigateToDreamJournal() {
    navigate(DreamJournalNavigation.DreamJournalRoute()) {
        launchSingleTop = true
        popUpTo(0)
    }
}
