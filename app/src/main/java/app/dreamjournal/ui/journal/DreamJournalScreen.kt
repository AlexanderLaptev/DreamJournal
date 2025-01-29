package app.dreamjournal.ui.journal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableDefaults
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.R
import app.dreamjournal.data.di.mockDatabaseModule
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.ui.AppNavigationBar
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.theme.DreamJournalTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@Composable
fun DreamJournalScreen(
    navController: NavController,
    onDreamClick: (DreamWithTags) -> Unit,
    viewModel: DreamJournalViewModel = koinViewModel(),
) {
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopSearchBar(
                query = query,
                expanded = expanded,
                onQueryChange = { query = it },
                onExpandedChange = { expanded = it },
                onSearch = {},
                onCalendar = {},
                onSettings = {},
            )
        },

        bottomBar = {
            AnimatedVisibility(
                visible = !expanded,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) { AppNavigationBar(navController) }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (uiState) {
                is DreamJournalScreenUiState.Loaded -> {
                    val formatter = rememberDateTimeFormatter()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                                bottom = 0.dp,
                                start = 16.dp,
                                end = 16.dp,
                            ),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        (uiState as DreamJournalScreenUiState.Loaded)
                            .groups
                            .forEach {
                                dreamGroup(
                                    dreamGroupUiState = it,
                                    cardFormatter = formatter,
                                    onDreamClick = onDreamClick,
                                )
                            }
                    }
                }

                DreamJournalScreenUiState.Loading -> {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }

                DreamJournalScreenUiState.NoEntries -> {
                    Text(
                        text = "No entries",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DreamJournalScreen_(
    onDreamClick: (DreamWithTags) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DreamJournalViewModel = koinViewModel(),
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
                val overscroll = ScrollableDefaults.overscrollEffect()
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val state = uiState as DreamJournalScreenUiState.Loaded
                    state.groups.forEach {
                        dreamGroup(it, formatter, onDreamClick)
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
        androidLogger(Level.DEBUG)
        androidContext(context)
        modules(mockDatabaseModule, module {
            viewModelOf(::DreamJournalViewModel)
        })
    }) {
        DreamJournalTheme {
            DreamJournalScreen(rememberNavController(), koinViewModel())
        }
    }
}
