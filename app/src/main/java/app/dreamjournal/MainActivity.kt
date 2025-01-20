package app.dreamjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.navigation.ApplicationNavigationFlow
import app.dreamjournal.ui.components.navbars.BottomNavigationBar
import app.dreamjournal.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    ApplicationNavigationFlow(
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}
