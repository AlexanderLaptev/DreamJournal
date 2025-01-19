package app.dreamjournal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.dreamjournal.ui.theme.TextColor
import app.dreamjournal.ui.theme.getDarkVersion

@Composable
fun TagView(
    emoji: String = "",
    tagText: String = "",
    containerColor: Color
) {
    val tagViewHeight = 32.dp // TODO: fix the height
    val roundedCornerShape = RoundedCornerShape(8.dp)

    val verticalPadding = 6.dp
    val horizontalPadding = 8.dp
    val spaceBetweenEmojiAndText = 4.dp

    val emojiFontSize = 20.sp
    val textFontSize = 16.sp

    Box(
        modifier = Modifier
            .background(
                color = containerColor,
                shape = roundedCornerShape
            )
            .padding(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            )
            .height(tagViewHeight),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = emoji, fontSize = emojiFontSize)
            Spacer(modifier = Modifier.width(spaceBetweenEmojiAndText))
            Text(text = tagText, fontSize = textFontSize, color = containerColor.getDarkVersion())
        }
    }
}

@Preview
@Composable
fun TagViewWithEmojiPreview() {
    TagView(emoji = "😄", tagText = "with emoji", containerColor = TextColor)
}

@Preview
@Composable
fun TagViewWithoutEmojiPreview() {
    TagView(emoji = "", tagText = "without emoji", containerColor = TextColor)
}
