package app.dreamjournal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme

fun NavGraphBuilder.settingsDestination() {
    composable<ApplicationNavigation.Settings> { SettingsScreen() }
}

@Composable
fun SettingsScreen() {
    // Plug
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen")
    }
}

@Preview
@Composable
private fun Preview() {
    ApplicationTheme {
        SettingsScreen()
    }
}
