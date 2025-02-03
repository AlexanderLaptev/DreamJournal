package app.dreamjournal.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.ui.faq.faqDestination
import app.dreamjournal.ui.journal.details.dreamDetailsDestination
import app.dreamjournal.ui.journal.details.navigateToDreamDetails
import app.dreamjournal.ui.journal.dreamJournalDestination
import app.dreamjournal.ui.journal.navigateToDreamJournal
import app.dreamjournal.ui.settings.settingsDestination
import app.dreamjournal.ui.statistics.statisticsDestination
import app.dreamjournal.ui.tools.toolsDestination

@Composable
fun DreamJournalNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val onBackPressed = fun() { navController.navigateToDreamJournal() }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DreamJournalNavigation.DreamJournalRoute(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        dreamJournalDestination(
            navController = navController,
            onDreamClick = {
                navController.navigateToDreamDetails(it.dream.id)
            },
        )
        dreamDetailsDestination(onBack = onBackPressed)
        statisticsDestination (onBack = onBackPressed)
        toolsDestination(onBack = onBackPressed)
        faqDestination(onBack = onBackPressed)
        settingsDestination(onBack = onBackPressed)
    }
}
