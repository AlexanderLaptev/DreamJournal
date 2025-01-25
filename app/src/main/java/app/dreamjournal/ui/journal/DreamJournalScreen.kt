package app.dreamjournal.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.R
import app.dreamjournal.appModules
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun DreamJournalScreen(
    viewModel: DreamJournalViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val formatter = rememberDateTimeFormatter(useShortFormat = true)

    val padding = PaddingValues(
        start = 16.dp,
        top = 16.dp,
        end = 16.dp,
        bottom = 0.dp,
    )

    val italicFontStyle = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic)

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth(),
    ) {
        when (uiState) {
            is DreamJournalScreenUiState.Loaded -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val state = uiState as DreamJournalScreenUiState.Loaded
                    state.groups.forEach {
                        dreamGroup(it, formatter)
                    }
                }
            }

            DreamJournalScreenUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Pending,
                        contentDescription = "",
                        modifier = Modifier.size(128.dp),
                        tint = CatppuccinColors.overlay0,
                    )
                    Text(
                        text = stringResource(R.string.journal_loading_message),
                        style = italicFontStyle,
                        color = CatppuccinColors.overlay0,
                    )
                }
            }

            DreamJournalScreenUiState.NoEntries -> {
                Text(
                    text = stringResource(R.string.journal_empty_message),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = italicFontStyle,
                    color = CatppuccinColors.overlay0,
                )
            }
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
