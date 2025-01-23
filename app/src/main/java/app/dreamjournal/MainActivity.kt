package app.dreamjournal

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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

                var navigationBarVisible by rememberSaveable { mutableStateOf(true) }
                if (currentDestination != null) {
                    navigationBarVisible = when {
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
                            visible = navigationBarVisible,
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
                                navController = navController,
                                onSearchExpandedChange = { navigationBarVisible = !it },
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

    private fun checkAndRequestPermissions() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            }

            Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }

            else -> {
                if (!isExternalStorageManagerGranted()) {
                    showManageStoragePermissionDialog()
                }
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            if (permission.values.all { it }) {
                Toast.makeText(
                    this,
                    "All permissions is granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permissions required for the application to work",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        permissionLauncher.launch(permissions)
    }

    private fun isExternalStorageManagerGranted(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            android.os.Environment.isExternalStorageManager()
        } else {
            true
        }

    // Replace alert dialog with composable
    @RequiresApi(Build.VERSION_CODES.R)
    private fun showManageStoragePermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Access to storage")
            .setMessage(
                "File management access must be granted for the application to work.\n" +
                        "Please, provide access in the settings"
            )
            .setPositiveButton("Provide access") { _, _ ->
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:$packageName")
                }

                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
