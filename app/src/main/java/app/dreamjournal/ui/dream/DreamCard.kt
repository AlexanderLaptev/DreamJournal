package app.dreamjournal.ui.dream

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DreamCard(
    dream: Dream,
    tags: List<Tag>,
) {
    // Card
    Column(
        modifier = Modifier
            .background(color = mocha_crust, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
    ) {

        // Contents & tags
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Color bar & contents
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Color bar
                val barColor = TagColor.entries[dream.colorIndex].body
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .background(color = barColor, shape = CircleShape)
                        .fillMaxHeight(),
                ) {}

                // Contents
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Title & time
                        Column {
                            if (dream.title.isNotEmpty()) {
                                Text(
                                    text = dream.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = mocha_text,
                                )
                            }

                            val timeText = let {
                                val instant = Instant.ofEpochMilli(dream.epochMilli)
                                val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
                                formatter.format(dateTime)
                            }
                            Text(
                                text = timeText,
                                style = MaterialTheme.typography.titleSmall,
                                color = mocha_overlay0,
                            )
                        }

                        // Icons
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            val iconSize = 32.dp
                            if (dream.isLucid) {
                                Icon(
                                    imageVector = Icons.Rounded.Brightness7,
                                    contentDescription = "",
                                    modifier = Modifier.size(iconSize),
                                    tint = mocha_mauve,
                                )
                            }

                            if (dream.isFavorite) {
                                Icon(
                                    imageVector = Icons.Rounded.Grade,
                                    contentDescription = "",
                                    modifier = Modifier.size(iconSize),
                                    tint = mocha_yellow,
                                )
                            }
                        }
                    }

                    Text(
                        text = dream.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = mocha_subtext1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            // Tags
            if (tags.isNotEmpty()) {
                val spacing = 8.dp
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(spacing),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    overflow = FlowRowOverflow.Visible,
                ) {
                    for (tag in tags) Tag(tag)
                }
            }
        }
    }
}

private val PREVIEW_INSTANT = LocalDateTime.of(2025, 1, 15, 7, 35, 20).toInstant(ZoneOffset.UTC)
private val PREVIEW_DREAM = Dream(
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras blandit lacus non metus gravida interdum. Duis sed congue mauris. Vivamus a magna tristique, semper diam vitae, scelerisque turpis. In efficitur purus massa, non aliquam turpis sagittis nec. Quisque sit amet dui commodo, tempor dui nec, pharetra ligula.",
    PREVIEW_INSTANT.toEpochMilli(),
    "Lorem ipsum dolor sit amet",
    true,
    2,
    true,
    3,
)
private val PREVIEW_TAGS = listOf(
    Tag("lorem ipsum", "😀", 0),
    Tag("dolor sit", "", 2),
    Tag("tellus non", "😁", 4),
    Tag("auctor yelit", "😎", 3),
    Tag("tellus non", "😁", 4),
    Tag("lorem ipsum", "😀", 0),
    Tag("auctor yelit", "😎", 3),
)

@Preview
@Composable
private fun PreviewWithTitle() {
    DreamCard(PREVIEW_DREAM, PREVIEW_TAGS)
}

@Preview
@Composable
private fun PreviewNoTitle() {
    DreamCard(PREVIEW_DREAM.copy(title = ""), PREVIEW_TAGS)
}
