package app.dreamjournal.ui.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class ApplicationNavigation(val route: String) {
    data object DreamJournal : ApplicationNavigation(route = "DreamJournal")
    data object Statistics : ApplicationNavigation(route = "Statistics")
    data object Tools : ApplicationNavigation(route = "Tools")
    data object Info : ApplicationNavigation(route = "Info")
}
