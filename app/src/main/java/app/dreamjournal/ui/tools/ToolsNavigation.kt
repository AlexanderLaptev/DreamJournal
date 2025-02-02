package app.dreamjournal.ui.tools

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.toolsDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.ToolsRoute> {
        ToolsScreen()
    }
}

fun NavController.navigateToTools(
    onBack: () -> Unit,
) {
    navigate(DreamJournalNavigation.ToolsRoute)
}
