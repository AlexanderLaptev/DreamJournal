package app.dreamjournal.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.rounded.InsertChartOutlined
import androidx.compose.ui.graphics.vector.ImageVector
import app.dreamjournal.R

data class TopLevelRoute<T : Any>(
    @StringRes val name: Int,
    val icon: ImageVector,
    val route: T,
)

val topLevelRoutes = listOf(
    TopLevelRoute(
        R.string.screen_journal,
        Icons.AutoMirrored.Rounded.ListAlt,
        ApplicationNavigation.DreamJournal(),
    ),
    TopLevelRoute(
        R.string.screen_statistics,
        Icons.Rounded.InsertChartOutlined,
        ApplicationNavigation.Statistics,
    ),
    TopLevelRoute(
        R.string.screen_tools,
        Icons.Outlined.Inventory2,
        ApplicationNavigation.Tools,
    ),
    TopLevelRoute(
        R.string.screen_faq,
        Icons.AutoMirrored.Rounded.HelpOutline,
        ApplicationNavigation.Faq,
    ),
)
