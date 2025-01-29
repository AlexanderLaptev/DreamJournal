package app.dreamjournal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.ui.journal.details.dreamDetailsDestination
import app.dreamjournal.ui.journal.details.navigateToDreamDetails
import app.dreamjournal.ui.journal.dreamJournalDestination
import app.dreamjournal.ui.journal.navigateToDreamJournal

@Composable
fun DreamJournalNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DreamJournalNavigation.DreamJournalRoute(),
    ) {
        dreamJournalDestination(
            navController = navController,
            onDreamClick = {
                navController.navigateToDreamDetails(it.dream.id)
            },
        )
        dreamDetailsDestination(
            onBack = {
                navController.navigateToDreamJournal()
            }
        )
    }
}
