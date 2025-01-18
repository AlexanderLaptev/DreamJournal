package app.dreamjournal.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class ApplicationNavigation(val route: String) {
    data object DreamJournal: ApplicationNavigation(route = "DreamJournal")
    data object Statistics: ApplicationNavigation(route = "Statistics")
    data object Tools: ApplicationNavigation(route = "Tools")
    data object Wiki: ApplicationNavigation(route = "Wiki")
    data object Settings: ApplicationNavigation(route = "Settings")
}
