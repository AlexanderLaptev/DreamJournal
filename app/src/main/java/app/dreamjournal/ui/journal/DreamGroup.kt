package app.dreamjournal.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.data.dream.MockDreamRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.ui.shared.DreamCard
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun LazyListScope.dreamGroup(
    dreamGroupUiState: DreamGroupUiState,
    cardFormatter: DateTimeFormatter,
) {
    item {
        val pattern = stringResource(R.string.group_date_pattern)
        val formatter = rememberDateTimeFormatter(pattern)
        val dateText = formatter.format(dreamGroupUiState.date)
        Text(
            text = dateText,
            style = MaterialTheme.typography.titleLarge,
            color = CatppuccinColors.subtext0,
        )
    }

    items(dreamGroupUiState.dreamsWithTags) { DreamCard(it.dream, it.tags, cardFormatter) }
}

private val PREVIEW_DREAM_GROUP = run {
    val random = Random(8974)
    DreamGroupUiState(
        LocalDate.of(2025, 1, 15),
        listOf(
            DreamWithTags(
                MockDreamRepository.getRandomDream(random),
                listOf(
                    Tag("lorem", color = TagColor.Red),
                    Tag("ipsum", color = TagColor.Yellow),
                )
            ),
            DreamWithTags(
                MockDreamRepository.getRandomDream(random),
                listOf(
                    Tag("dolor", color = TagColor.White),
                    Tag("sit", color = TagColor.Blue),
                )
            ),
        ),
    )
}

@Preview
@Composable
private fun Preview() {
    ApplicationTheme {
        val formatter = rememberDateTimeFormatter()
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            dreamGroup(PREVIEW_DREAM_GROUP, formatter)
        }
    }
}
