package app.dreamjournal.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Help
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.InsertChart
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationBarItem(
    val route: String,
    val name: String,
    val icon: ImageVector
) {
    data object DreamJournal : BottomNavigationBarItem(
        route = ApplicationNavigation.DreamJournal.route,
        name = "Journal",
        icon = Icons.AutoMirrored.Rounded.ListAlt
    )

    data object Statistics : BottomNavigationBarItem(
        route = ApplicationNavigation.Statistics.route,
        name = "Statistics",
        icon = Icons.Rounded.InsertChart
    )

    data object Tools : BottomNavigationBarItem(
        route = ApplicationNavigation.Tools.route,
        name = "Tools",
        icon = Icons.Rounded.Inventory2
    )

    data object Info : BottomNavigationBarItem(
        route = ApplicationNavigation.Info.route,
        name = "Info",
        icon = Icons.AutoMirrored.Rounded.Help
    )
}

val bottomNavbarItems = listOf(
    BottomNavigationBarItem.DreamJournal,
    BottomNavigationBarItem.Statistics,
    BottomNavigationBarItem.Tools,
    BottomNavigationBarItem.Info,
)
