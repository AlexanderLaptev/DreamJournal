package app.dreamjournal.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.ui.shared.DreamCard
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun LazyListScope.dreamGroup(
    dreamGroupUiState: DreamGroupUiState,
    cardFormatter: DateTimeFormatter,
) {
    item {
        val configuration = LocalConfiguration.current
        val primaryLocale = configuration.locales[0]
        val pattern = stringResource(R.string.group_date_pattern)
        val formatter = remember { DateTimeFormatter.ofPattern(pattern, primaryLocale) }
        val dateText = formatter.format(dreamGroupUiState.date)

        Text(
            text = dateText,
            style = MaterialTheme.typography.titleLarge,
            color = CatppuccinColors.subtext0,
        )
    }

    items(dreamGroupUiState.dreamsWithTags) { DreamCard(it.dream, it.tags, cardFormatter) }
}

private val PREVIEW_DREAM_GROUP = DreamGroupUiState(
    LocalDate.of(2025, 1, 15),
    listOf(
        DreamWithTags(
            Dream(
                title = "Lorem ipsum",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                instant = LocalDateTime.of(2025, 1, 15, 7, 34, 13).toInstant(ZoneOffset.UTC),
                color = TagColor.Purple,
            ),
            listOf(
                Tag("lorem", color = TagColor.Red),
                Tag("ipsum", color = TagColor.Yellow),
            )
        ),
        DreamWithTags(
            Dream(
                title = "Dolor sit",
                content = "Mauris porta, sem vel volutpat tincidunt.",
                instant = LocalDateTime.of(2025, 1, 15, 7, 28, 22).toInstant(ZoneOffset.UTC),
                color = TagColor.Green,
            ),
            listOf(
                Tag("dolor", color = TagColor.White),
                Tag("sit", color = TagColor.Blue),
            )
        ),
    ),
)

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
