package app.dreamjournal.ui.journal

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DreamJournalScreen(
) {
}

@Preview
@Composable
fun DreamJournalScreenPreview() {
    ApplicationTheme {
        DreamJournalScreen()
    }
}

fun NavGraphBuilder.dreamJournalDestination() {
    composable<ApplicationNavigation.DreamJournal> {
        DreamJournalScreen()
    }
}
