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
import app.dreamjournal.data.dream.MockDreamRepository
import app.dreamjournal.data.dream.MockTagRepository
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.theme.FavoriteColor
import app.dreamjournal.ui.theme.LucidColor
import app.dreamjournal.ui.util.toShortText
import app.dreamjournal.ui.util.toText
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun DreamCard(
    dream: Dream,
    tags: List<Tag>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardColors = CardColors(
        containerColor = CatppuccinColors.crust,
        contentColor = CatppuccinColors.text,
        disabledContainerColor = CatppuccinColors.crust,
        disabledContentColor = CatppuccinColors.text,
    )

    Card(
        modifier = modifier.height(IntrinsicSize.Max),
        onClick = onClick,
        colors = cardColors,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ColorStrip(dream.color.getColor(CatppuccinColors))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardHeader(
                    title = dream.title,
                    dateTime = LocalDateTime.ofInstant(dream.created, ZoneId.systemDefault()),
                    lucid = dream.isLucid,
                    favorite = dream.isFavorite,
                )

                Text(
                    text = dream.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CatppuccinColors.subtext1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                CardTags(tags)
            }
        }
    }
}

@Composable
fun ColorStrip(
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
                color = if (title.isBlank()) CatppuccinColors.overlay0 else CatppuccinColors.text,
            )

            // TODO: add an option to toggle the date
            DotSeparatedString(
                dateTime.toLocalDate().toShortText(),
                dateTime.toLocalTime().toText(),
                style = MaterialTheme.typography.titleSmall,
                color = CatppuccinColors.overlay0,
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
                    tint = LucidColor,
                )
            }
            if (favorite) {
                Icon(
                    imageVector = Icons.Rounded.Grade,
                    contentDescription = "",
                    modifier = Modifier.size(iconSize),
                    tint = FavoriteColor,
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

private val PREVIEW_DREAM = MockDreamRepository.MOCK_DREAMS[0]

private val PREVIEW_TAGS = MockTagRepository.MOCK_TAGS

@Preview
@Composable
private fun PreviewWithTitle() {
    DreamCard(
        dream = PREVIEW_DREAM,
        tags = PREVIEW_TAGS.subList(0, 3),
        onClick = {}
    )
}

@Preview
@Composable
private fun PreviewTagOverflow() {
    DreamCard(
        dream = PREVIEW_DREAM,
        tags = PREVIEW_TAGS,
        onClick = {}
    )
}

@Preview
@Composable
private fun PreviewNoTitle() {
    DreamCard(
        dream = PREVIEW_DREAM.copy(title = ""),
        tags = PREVIEW_TAGS.subList(0, 3),
        onClick = {},
    )
}
