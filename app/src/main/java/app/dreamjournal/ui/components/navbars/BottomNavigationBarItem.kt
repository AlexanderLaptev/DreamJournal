package app.dreamjournal.ui.components.navbars

import androidx.annotation.DrawableRes
import app.dreamjournal.R
import app.dreamjournal.navigation.ApplicationNavigation

sealed class BottomNavigationBarItem(
    val route: String,
    val name: String,
    @DrawableRes val icon: Int
) {
    data object DreamJournal : BottomNavigationBarItem(
        route = ApplicationNavigation.DreamJournal.route,
        name = "DreamJournalScreen",
        icon = R.drawable.dream_journal
    )

    data object Statistics : BottomNavigationBarItem(
        route = ApplicationNavigation.Statistics.route,
        name = "StatisticsScreen",
        icon = R.drawable.statistics
    )

    data object Tools : BottomNavigationBarItem(
        route = ApplicationNavigation.Tools.route,
        name = "ToolsScreen",
        icon = R.drawable.tools
    )

    data object Wiki : BottomNavigationBarItem(
        route = ApplicationNavigation.Wiki.route,
        name = "WikiScreen",
        icon = R.drawable.wiki
    )

    data object Settings : BottomNavigationBarItem(
        route = ApplicationNavigation.Settings.route,
        name = "SettingsScreen",
        icon = R.drawable.settings
    )
}

val bottomNavBarItems = listOf(
    BottomNavigationBarItem.DreamJournal,
    BottomNavigationBarItem.Statistics,
    BottomNavigationBarItem.Tools,
    BottomNavigationBarItem.Wiki,
    BottomNavigationBarItem.Settings
)
