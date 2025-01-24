package app.dreamjournal.ui.shared

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.dreamjournal.data.dream.Tag
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors

@Composable
fun Tag(tag: Tag, modifier: Modifier = Modifier) {
    val emojiTextStyle = TextStyle(
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    )
    val labelTextStyle = TextStyle(
        fontSize = 16.sp,
    )

    val padding = 8.dp
    val minSize = 32.dp
    val spacing = 4.dp

    val tagColor = tag.color
    val containerColor = tagColor.getColor(CatppuccinColors)

    Box(
        modifier = modifier
            .background(containerColor, MaterialTheme.shapes.small)
            .defaultMinSize(minSize)
            .padding(horizontal = padding, vertical = 0.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.defaultMinSize(minHeight = minSize),
        ) {
            if (tag.emoji.isNotEmpty()) {
                Text(
                    text = tag.emoji,
                    style = emojiTextStyle,
                )
            }

            if (tag.label.isNotEmpty()) {
                Text(
                    text = tag.label,
                    style = labelTextStyle,
                    color = CatppuccinColors.base,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
    }
}

private const val PREVIEW_TEXT = "lorem ipsum"
private const val PREVIEW_TEXT_SHORT = "..."
private const val PREVIEW_EMOJI = "😀"

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewNoEmoji() {
    ApplicationTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (entry in TagColor.entries) {
                val tag = Tag(PREVIEW_TEXT, color = entry)
                Tag(tag)
            }
            Tag(Tag(PREVIEW_TEXT_SHORT))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewWithEmoji() {
    ApplicationTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (entry in TagColor.entries) {
                val tag = Tag(PREVIEW_TEXT, emoji = PREVIEW_EMOJI, color = entry)
                Tag(tag)
            }
            Tag(Tag("", emoji = PREVIEW_EMOJI))
        }
    }
}
