package app.dreamjournal.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.data.dream.DreamWithTags
import app.dreamjournal.data.dream.MockDreamRepository
import app.dreamjournal.data.dream.MockTagRepository
import app.dreamjournal.ui.shared.DreamCard
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.theme.DreamJournalTheme
import app.dreamjournal.ui.util.toLongText
import java.time.LocalDate

fun LazyListScope.dreamGroup(
    dreamGroupUiState: DreamGroupUiState,
    onDreamClick: (DreamWithTags) -> Unit,
) {
    item {
        Text(
            text = dreamGroupUiState.date.toLongText(),
            style = MaterialTheme.typography.titleLarge,
            color = CatppuccinColors.subtext0,
        )
    }

    items(
        items = dreamGroupUiState.dreamsWithTags,
        key = { it.dream.id }
    ) { withTags ->
        DreamCard(
            dream = withTags.dream,
            tags = withTags.tags,
            onClick = { onDreamClick(withTags) },
        )
    }
}

private val PREVIEW_DREAM_GROUP = run {
    DreamGroupUiState(
        LocalDate.of(2025, 1, 15),
        listOf(
            DreamWithTags(
                MockDreamRepository.MOCK_DREAMS[0],
                MockTagRepository.MOCK_TAGS.take(5)
            ),
            DreamWithTags(
                MockDreamRepository.MOCK_DREAMS[1],
                MockTagRepository.MOCK_TAGS.reversed().take(2)
            ),
        ),
    )
}

@Preview
@Composable
private fun Preview() {
    DreamJournalTheme {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            dreamGroup(
                dreamGroupUiState = PREVIEW_DREAM_GROUP,
                onDreamClick = {},
            )
        }
    }
}
