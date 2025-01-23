package app.dreamjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.ui.SettingsScreen
import app.dreamjournal.ui.StatisticsScreen
import app.dreamjournal.ui.ToolsScreen
import app.dreamjournal.ui.WikiScreen
import app.dreamjournal.ui.calendar.CalendarScreen
import app.dreamjournal.ui.journal.DreamJournalScreen
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.navigation.BottomNavigationBar
import app.dreamjournal.ui.theme.ApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                var bottomBarVisible by rememberSaveable { mutableStateOf(true) }
                if (currentDestination != null) {
                    bottomBarVisible = when {
                        currentDestination.hasRoute(ApplicationNavigation.Calendar::class) -> {
                            false
                        }

                        else -> true
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomBarVisible,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it }),
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues = innerPadding),
                        navController = navController,
                        startDestination = ApplicationNavigation.DreamJournal,
                    ) {
                        composable<ApplicationNavigation.DreamJournal> {
                            DreamJournalScreen(
                                navController = navController
                            )
                        }
                        composable<ApplicationNavigation.Calendar> { CalendarScreen() }
                        composable<ApplicationNavigation.Statistics> { StatisticsScreen() }
                        composable<ApplicationNavigation.Tools> { ToolsScreen() }
                        composable<ApplicationNavigation.Wiki> { WikiScreen() }
                        composable<ApplicationNavigation.Settings> { SettingsScreen() }
                    }
                }
            }
        }
    }
}
