package app.dreamjournal.ui.journal.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness7
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.data.dream.TagColor
import app.dreamjournal.ui.shared.ColorStrip
import app.dreamjournal.ui.shared.DotSeparatedString
import app.dreamjournal.ui.shared.getColor
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.theme.DreamJournalTheme
import app.dreamjournal.ui.theme.LucidColor
import app.dreamjournal.ui.util.toShortText
import app.dreamjournal.ui.util.toText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun DreamHeader(
    title: String,
    created: Instant,
    isLucid: Boolean,
    stripColor: Color,
    modifier: Modifier = Modifier,
) {
    var titleStyle = MaterialTheme.typography.titleLarge
    if (title.isBlank()) titleStyle = titleStyle.copy(fontStyle = FontStyle.Italic)
    val textColor = CatppuccinColors.text

    Row(
        modifier = modifier
            .background(color = CatppuccinColors.base)
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ColorStrip(color = stripColor)

        Column(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = title.ifBlank { stringResource(R.string.journal_entry_untitled) },
                style = titleStyle,
                color = textColor
            )

            val dateTime = LocalDateTime.ofInstant(created, ZoneId.systemDefault())
            DotSeparatedString(
                dateTime.toLocalDate().toShortText(),
                dateTime.toLocalTime().toText(),
                style = MaterialTheme.typography.titleMedium,
                color = CatppuccinColors.overlay0,
            )
        }

        Icon(
            modifier = modifier.size(36.dp),
            imageVector = Icons.Rounded.Brightness7,
            contentDescription = stringResource(R.string.label_lucid),
            tint = if (isLucid) LucidColor else Color.Transparent
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    val now = LocalDateTime.of(2025, 1, 15, 13, 35, 20)
    DreamJournalTheme {
        DreamHeader(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            created = now.toInstant(ZoneOffset.UTC),
            isLucid = true,
            stripColor = TagColor.Yellow.getColor(CatppuccinColors),
        )
    }
}
