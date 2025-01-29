package app.dreamjournal.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import app.dreamjournal.R
import kotlinx.serialization.Serializable

sealed interface DreamJournalNavigation {
    @Serializable
    data class DreamJournalRoute(val searchQuery: String? = null) : DreamJournalNavigation

    @Serializable
    data class DreamDetailsRoute(val dreamId: Long) : DreamJournalNavigation

    @Serializable
    data object StatisticsRoute : DreamJournalNavigation

    @Serializable
    data object ToolsRoute : DreamJournalNavigation

    @Serializable
    data object FaqRoute : DreamJournalNavigation

    @Serializable
    data object SettingsRoute : DreamJournalNavigation
}

data class TopLevelDestination(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: DreamJournalNavigation,
)

val topLevelDestinations = listOf(
    TopLevelDestination(
        R.string.screen_journal,
        Icons.AutoMirrored.Rounded.ListAlt,
        DreamJournalNavigation.DreamJournalRoute(),
    ),
//    TopLevelDestination(
//        R.string.screen_statistics,
//        Icons.Rounded.InsertChartOutlined,
//        DreamJournalNavigation.StatisticsRoute,
//    ),
//    TopLevelDestination(
//        R.string.screen_tools,
//        Icons.Outlined.Inventory2,
//        DreamJournalNavigation.ToolsRoute,
//    ),
//    TopLevelDestination(
//        R.string.screen_faq,
//        Icons.AutoMirrored.Rounded.HelpOutline,
//        DreamJournalNavigation.FaqRoute,
//    ),
)

@Composable
fun AppNavigationBar(
    navController: NavController,
) {
    NavigationBar {
        topLevelDestinations.forEach {
            val label = stringResource(it.label)
            NavigationBarItem(
                selected = navController.currentDestination?.hasRoute(it.route::class) == true,
                onClick = { navController.navigate(it.route) },
                label = { Text(label) },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = "",
                        tint = LocalContentColor.current,
                    )
                }
            )
        }
    }
}
