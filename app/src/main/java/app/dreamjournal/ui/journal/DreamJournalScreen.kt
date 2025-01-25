package app.dreamjournal.ui.journal

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.appModules
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DreamJournalScreen(
    viewModel: DreamJournalViewModel,
) {
}

@Preview
@Composable
fun DreamJournalScreenPreview() {
    val context = LocalContext.current
    KoinApplication(application = {
        androidContext(context)
        appModules()
    }) {
        ApplicationTheme {
            DreamJournalScreen(koinViewModel())
        }
    }
}

fun NavGraphBuilder.dreamJournalDestination(
    viewModel: DreamJournalViewModel,
) {
    composable<ApplicationNavigation.DreamJournal> {
        DreamJournalScreen(viewModel)
    }
}
