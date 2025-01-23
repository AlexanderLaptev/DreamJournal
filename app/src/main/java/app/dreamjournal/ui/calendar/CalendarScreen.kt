package app.dreamjournal.ui.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.dreamjournal.ui.theme.ApplicationTheme

@Composable
fun CalendarScreen() {
    // Plug
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Calendar()
    }
}

@Preview
@Composable
private fun Preview() {
    ApplicationTheme {
        CalendarScreen()
    }
}
