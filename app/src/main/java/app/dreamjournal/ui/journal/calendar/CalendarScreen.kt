package app.dreamjournal.ui.journal.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme

fun NavGraphBuilder.calendarDestination() {
    composable<ApplicationNavigation.Calendar> {
        CalendarScreen()
    }
}

fun NavController.navigateToCalendar() {
    navigate(ApplicationNavigation.Calendar)
}

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
