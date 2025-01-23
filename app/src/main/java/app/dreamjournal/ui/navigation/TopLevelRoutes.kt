package app.dreamjournal.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.rounded.InsertChartOutlined
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelRoute<T : Any>(
    val name: String,
    val icon: ImageVector,
    val route: T,
)

val topLevelRoutes = listOf(
    TopLevelRoute(
        "Journal",
        Icons.AutoMirrored.Rounded.ListAlt,
        ApplicationNavigation.DreamJournal
    ),
    TopLevelRoute(
        "Statistics",
        Icons.Rounded.InsertChartOutlined,
        ApplicationNavigation.Statistics
    ),
    TopLevelRoute(
        "Tools",
        Icons.Outlined.Inventory2,
        ApplicationNavigation.Tools,
    ),
    TopLevelRoute(
        "Wiki",
        Icons.AutoMirrored.Rounded.HelpOutline,
        ApplicationNavigation.Wiki,
    ),
)
