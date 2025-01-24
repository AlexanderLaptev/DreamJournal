package app.dreamjournal.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.Theme

fun NavGraphBuilder.settingsDestination(onThemeChange: (Theme) -> Unit) {
    composable<ApplicationNavigation.Settings> { SettingsScreen(onThemeChange = onThemeChange) }
}

fun NavController.navigateToSettings() {
    navigate(ApplicationNavigation.Settings)
}

@Composable
fun SettingsScreen(
    onThemeChange: (Theme) -> Unit
) {
    val buttonHeight = 48.dp
    val buttonWidth = 160.dp

    val buttonModifier = Modifier
        .width(buttonWidth)
        .height(buttonHeight)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = buttonModifier,
            onClick = {
                onThemeChange(Theme.Light)
            }
        ) {
            Text(text = "Light Theme")
        }

        SpacerBetweenButtons()

        Button(
            modifier = buttonModifier,
            onClick = {
                onThemeChange(Theme.Dark)
            }
        ) {
            Text(text = "Dark Theme")
        }

        SpacerBetweenButtons()

        Button(
            modifier = buttonModifier,
            onClick = {
                onThemeChange(Theme.System)
            }
        ) {
            Text(text = "System Theme")
        }
    }
}

@Composable
fun SpacerBetweenButtons() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun Preview() {
    ApplicationTheme {
        SettingsScreen({})
    }
}
