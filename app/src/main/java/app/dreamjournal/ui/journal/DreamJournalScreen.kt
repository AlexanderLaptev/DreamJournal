package app.dreamjournal.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.appModules
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.ApplicationTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun DreamJournalScreen(
    viewModel: DreamJournalViewModel,
) {
    val dreamGroups by viewModel.dreamGroups.collectAsState()
    val formatter = rememberDateTimeFormatter(useShortFormat = true)

    LazyColumn(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 0.dp,
                start = 16.dp,
                end = 16.dp,
            ),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        dreamGroups.forEach {
            dreamGroup(it, formatter)
        }
    }
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
