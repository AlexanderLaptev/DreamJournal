package app.dreamjournal.ui.journal.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.dreamjournal.ui.shared.getColor
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.util.countWords
import org.koin.androidx.compose.koinViewModel

@Composable
fun DreamDetailsScreen(
    dreamId: Long,
    onBack: () -> Unit,
) {
    val viewModel = koinViewModel<DreamDetailsViewModel>()
    val dreamWithTags by viewModel.dream.collectAsStateWithLifecycle()
    LaunchedEffect(dreamId) { viewModel.loadDream(dreamId) }

    Scaffold(
        topBar = {
            DetailsTopBar(
                isFavorite = dreamWithTags?.dream?.isFavorite == true,
                onBack = onBack,
            )
        },
    ) { padding ->
        val withTags = dreamWithTags ?: return@Scaffold
        val dream = withTags.dream
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                DreamHeader(
                    title = dream.title,
                    created = dream.created,
                    isLucid = dream.isLucid,
                    stripColor = dream.color.getColor(CatppuccinColors),
                )
            }

            item {
                Text(
                    text = dream.content,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            item {
                MinStatistic(
                    wordCount = dream.content.countWords(),
                    bedtime = dream.bedtime,
                    wakeUpTime = dream.wakeUpTime,
                )
            }

            item {
                TagList(withTags.tags)
            }
        }
    }
}
