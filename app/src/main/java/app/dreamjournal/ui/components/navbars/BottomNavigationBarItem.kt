package app.dreamjournal.ui.components.navbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.InsertChart
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.ui.graphics.vector.ImageVector
import app.dreamjournal.navigation.ApplicationNavigation

sealed class BottomNavigationBarItem(
    val route: String,
    val name: String,
    val icon: ImageVector
) {
    data object DreamJournal : BottomNavigationBarItem(
        route = ApplicationNavigation.DreamJournal.route,
        name = "DreamJournalScreen",
        icon = Icons.AutoMirrored.Rounded.ListAlt
    )

    data object Statistics : BottomNavigationBarItem(
        route = ApplicationNavigation.Statistics.route,
        name = "StatisticsScreen",
        icon = Icons.Rounded.InsertChart
    )

    data object Tools : BottomNavigationBarItem(
        route = ApplicationNavigation.Tools.route,
        name = "ToolsScreen",
        icon = Icons.Rounded.Inventory2
    )

    data object Wiki : BottomNavigationBarItem(
        route = ApplicationNavigation.Wiki.route,
        name = "WikiScreen",
        icon = Icons.AutoMirrored.Rounded.Help
    )
}

val bottomNavBarItems = listOf(
    BottomNavigationBarItem.DreamJournal,
    BottomNavigationBarItem.Statistics,
    BottomNavigationBarItem.Tools,
    BottomNavigationBarItem.Wiki,
)
