package app.dreamjournal.ui.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation
import app.dreamjournal.ui.theme.ThemePreference

fun NavGraphBuilder.settingsDestination(
    onBack: () -> Unit,
    onThemeChange: (ThemePreference) -> Unit
) {
    composable<DreamJournalNavigation.SettingsRoute> {
        SettingsScreen(onBack = onBack, onThemeChange = onThemeChange)
    }
}

fun NavController.navigateToSettings() {
    navigate(DreamJournalNavigation.SettingsRoute) {
        launchSingleTop = true
    }
}
