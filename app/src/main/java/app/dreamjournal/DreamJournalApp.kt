package app.dreamjournal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.ui.faq.faqDestination
import app.dreamjournal.ui.journal.calendar.calendarDestination
import app.dreamjournal.ui.journal.dreamJournalDestination
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.navigation.topLevelRoutes
import app.dreamjournal.ui.settingsDestination
import app.dreamjournal.ui.statistics.statisticsDestination
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.tools.toolsDestination

@Composable
fun DreamJournalApp() {
    ApplicationTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = { FAB() },

            topBar = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) { MainSearchBar(navController) }
            },
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = ApplicationNavigation.DreamJournal(),
            ) {
                dreamJournalDestination()
                calendarDestination()
                statisticsDestination()
                toolsDestination()
                faqDestination()
                settingsDestination()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private inline fun MainSearchBar(
    navController: NavHostController,
    crossinline onSearchExpandedChange: (Boolean) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    var previousQuery by rememberSaveable { mutableStateOf("") }

    val onExpandedChange = { it: Boolean ->
        previousQuery = query
        expanded = it
        onSearchExpandedChange(it)
    }

    SearchBar(
        modifier = Modifier.focusRequester(focusRequester),
        expanded = expanded,
        onExpandedChange = onExpandedChange,

        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { query = it },
                placeholder = { Text(stringResource(R.string.journal_search_prompt)) },
                expanded = expanded,
                onExpandedChange = onExpandedChange,

                onSearch = {
                    expanded = false
                    onSearchExpandedChange(false)
                },

                leadingIcon = {
                    IconButton(
                        onClick = {
                            if (expanded) {
                                query = previousQuery
                                expanded = false
                                onSearchExpandedChange(false)
                            } else {
                                if (query.isNotEmpty()) {
                                    query = ""
                                } else {
                                    expanded = true
                                    focusRequester.requestFocus()
                                }
                            }
                        },
                    ) {
                        val icon = if (expanded || query.isNotBlank()) {
                            Icons.AutoMirrored.Rounded.ArrowBack
                        } else Icons.Rounded.Search

                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                        )
                    }
                },

                trailingIcon = {
                    if (query.isNotEmpty() && !expanded) return@InputField
                    val icon = when (expanded) {
                        true -> Icons.Rounded.Close
                        false -> Icons.Rounded.CalendarToday
                    }

                    IconButton(
                        onClick = {
                            if (expanded) {
                                query = ""
                            } else {
                                navController.navigate(ApplicationNavigation.Calendar)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                        )
                    }
                },
            )
        },
    ) { }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        topLevelRoutes.forEach { route ->
            NavigationBarItem(
                icon = { Icon(route.icon, contentDescription = "") },
                label = { Text(stringResource(route.name)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
private fun FAB() {
    FloatingActionButton(
        onClick = {},
    ) { Icon(Icons.Rounded.Add, "") }
}

@Preview
@Composable
private fun Preview() {
    DreamJournalApp()
}
