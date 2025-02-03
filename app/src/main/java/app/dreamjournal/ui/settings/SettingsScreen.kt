package app.dreamjournal.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.ui.theme.DreamJournalTheme
import app.dreamjournal.ui.theme.LocalThemeChangeProvider
import app.dreamjournal.ui.theme.ThemePreference

@Composable
fun SettingsScreen(
    onBack: () -> Unit = {}
) {
    val onThemeChange = LocalThemeChangeProvider.current
    BackHandler(onBack = onBack)

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
            onClick = { onThemeChange(ThemePreference.Light) }
        ) {
            Text(text = "Light Theme")
        }

        SpacerBetweenButtons()

        Button(
            modifier = buttonModifier,
            onClick = { onThemeChange(ThemePreference.Dark) }
        ) {
            Text(text = "Dark Theme")
        }

        SpacerBetweenButtons()

        Button(
            modifier = buttonModifier,
            onClick = { onThemeChange(ThemePreference.System) }
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
    DreamJournalTheme {
        SettingsScreen()
    }
}
