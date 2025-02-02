package app.dreamjournal.ui.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.settingsDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.SettingsRoute> {
        SettingsScreen { }
    }
}

fun NavController.navigateToSettings() {
    navigate(DreamJournalNavigation.SettingsRoute)
}
