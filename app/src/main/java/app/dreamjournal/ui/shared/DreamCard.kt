package app.dreamjournal.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness7
import androidx.compose.material.icons.rounded.Grade
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.data.dream.Dream
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.ui.theme.mocha_crust
import app.dreamjournal.ui.theme.mocha_mauve
import app.dreamjournal.ui.theme.mocha_overlay0
import app.dreamjournal.ui.theme.mocha_subtext1
import app.dreamjournal.ui.theme.mocha_text
import app.dreamjournal.ui.theme.mocha_yellow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm")

@Composable
fun DreamCard(
    dream: Dream,
    tags: List<Tag>,
    modifier: Modifier = Modifier,
) {
    val cardColors = CardColors(
        containerColor = mocha_crust,
        contentColor = mocha_text,
        disabledContainerColor = mocha_crust,
        disabledContentColor = mocha_text,
    )

    Card(
        modifier = modifier.height(IntrinsicSize.Max),
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ColorStrip(TagColor.entries[dream.colorIndex].body)

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val dateTime = let {
                    val instant = Instant.ofEpochMilli(dream.epochMilli)
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                }
                CardHeader(
                    title = dream.title,
                    dateTime = dateTime,
                    lucid = dream.isLucid,
                    favorite = dream.isFavorite,
                )

                Text(
                    text = dream.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = mocha_subtext1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                CardTags(tags)
            }
        }
    }
}

@Composable
private fun ColorStrip(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(4.dp)
            .background(color = color, shape = CircleShape)
            .fillMaxHeight(),
    ) {}
}

@Composable
private fun CardHeader(
    title: String,
    dateTime: LocalDateTime,
    lucid: Boolean,
    favorite: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.weight(1.0f),
        ) {
            var titleStyle = MaterialTheme.typography.titleMedium
            if (title.isBlank()) titleStyle = titleStyle.copy(fontStyle = FontStyle.Italic)
            Text(
                text = title.ifBlank { stringResource(R.string.journal_entry_untitled) },
                style = titleStyle,
                color = if (title.isBlank()) mocha_overlay0 else mocha_text,
            )

            Text(
                text = formatter.format(dateTime),
                style = MaterialTheme.typography.titleSmall,
                color = mocha_overlay0,
            )
        }

        // TODO: add proper content descriptions
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val iconSize = 32.dp
            if (lucid) {
                Icon(
                    imageVector = Icons.Rounded.Brightness7,
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = mocha_mauve,
                )
            }
            if (favorite) {
                Icon(
                    imageVector = Icons.Rounded.Grade,
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = mocha_yellow,
                )
            }
        }
    }
}

private val OVERFLOW_TAG = Tag("...")

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CardTags(tags: List<Tag>) {
    // FIXME: a bug in FlowRow causes a crash if there are multiple long tags
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxLines = 1,
        maxItemsInEachRow = 4,
        overflow = FlowRowOverflow.expandIndicator { Tag(OVERFLOW_TAG) }
    ) {
        tags.forEach { Tag(it) }
    }
}

private val PREVIEW_INSTANT = LocalDateTime.of(2025, 1, 15, 7, 35, 20).toInstant(ZoneOffset.UTC)

@Suppress("SpellCheckingInspection")
private val PREVIEW_DREAM = Dream(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras blandit lacus non metus gravida interdum. Duis sed congue mauris. Vivamus a magna tristique, semper diam vitae, scelerisque turpis. In efficitur purus massa, non aliquam turpis sagittis nec. Quisque sit amet dui commodo, tempor dui nec, pharetra ligula.",
    PREVIEW_INSTANT.toEpochMilli(),
    "Lorem ipsum dolor sit amet",
    true,
    2,
    true,
    1,
)

@Suppress("SpellCheckingInspection")
private val PREVIEW_TAGS = listOf(
    Tag("lorem ipsum", "😀", 0),
    Tag("dolor sit", "", 2),
    Tag("tellus non", "😁", 4),
    Tag("auctor yelit", "😎", 3),
    Tag("lacus metus", "😎", 1),
)

@Preview
@Composable
private fun PreviewWithTitle() {
    DreamCard(PREVIEW_DREAM, PREVIEW_TAGS.subList(0, 3))
}

@Preview
@Composable
private fun PreviewTagOverflow() {
    DreamCard(PREVIEW_DREAM, PREVIEW_TAGS)
}

@Preview
@Composable
private fun PreviewNoTitle() {
    DreamCard(PREVIEW_DREAM.copy(title = ""), PREVIEW_TAGS.subList(0, 3))
}
