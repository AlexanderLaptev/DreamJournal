package app.dreamjournal.ui.tools

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.toolsDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.ToolsRoute> {
        ToolsScreen(onBack = onBack)
    }
}

fun NavController.navigateToTools() {
    navigate(DreamJournalNavigation.ToolsRoute) {
        launchSingleTop = true
    }
}
