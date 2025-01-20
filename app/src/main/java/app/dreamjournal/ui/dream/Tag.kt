package app.dreamjournal.ui.dream

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import app.dreamjournal.ui.theme.tag_blue
import app.dreamjournal.ui.theme.tag_blue_text
import app.dreamjournal.ui.theme.tag_green
import app.dreamjournal.ui.theme.tag_green_text
import app.dreamjournal.ui.theme.tag_orange
import app.dreamjournal.ui.theme.tag_orange_text
import app.dreamjournal.ui.theme.tag_purple
import app.dreamjournal.ui.theme.tag_purple_text
import app.dreamjournal.ui.theme.tag_red
import app.dreamjournal.ui.theme.tag_red_text
import app.dreamjournal.ui.theme.tag_teal
import app.dreamjournal.ui.theme.tag_teal_text
import app.dreamjournal.ui.theme.tag_white
import app.dreamjournal.ui.theme.tag_white_text
import app.dreamjournal.ui.theme.tag_yellow
import app.dreamjournal.ui.theme.tag_yellow_text

@Composable
fun Tag(
    label: String,
    containerColor: Color,
    textColor: Color,
    emoji: String = "",
) {
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
    val minWidth = minSize - 2 * padding
    val spacing = 4.dp

    Column(
        modifier = Modifier
            .background(containerColor, MaterialTheme.shapes.small)
            .padding(horizontal = padding, vertical = 0.dp)
            .defaultMinSize(minWidth),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.defaultMinSize(minHeight = minSize),
        ) {
            if (emoji.isNotEmpty()) {
                Text(
                    text = emoji,
                    style = emojiTextStyle,
                )
            }

            if (label.isNotEmpty()) {
                Text(
                    text = label,
                    style = labelTextStyle,
                    color = textColor,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}

private val previewColors = listOf(
    tag_white to tag_white_text,
    tag_red to tag_red_text,
    tag_orange to tag_orange_text,
    tag_yellow to tag_yellow_text,
    tag_green to tag_green_text,
    tag_teal to tag_teal_text,
    tag_blue to tag_blue_text,
    tag_purple to tag_purple_text,
)

@Preview(showBackground = false)
@Composable
private fun PreviewNoEmoji() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (colors in previewColors) {
            Tag(
                label = "lorem ipsum",
                containerColor = colors.first,
                textColor = colors.second,
            )
        }
        Tag("...", tag_white, tag_white_text)
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewWithEmoji() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (colors in previewColors) {
            Tag(
                label = "lorem ipsum",
                containerColor = colors.first,
                textColor = colors.second,
                emoji = "😀",
            )
        }
        Tag("", emoji = "😀", containerColor = tag_white, textColor = tag_white_text)
    }
}
