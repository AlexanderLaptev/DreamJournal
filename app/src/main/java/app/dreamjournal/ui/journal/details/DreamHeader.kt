package app.dreamjournal.ui.journal.details

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.dreamjournal.R
import app.dreamjournal.ui.shared.ColorStrip
import app.dreamjournal.ui.theme.ApplicationTheme
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.theme.LucidColor
import java.time.LocalDateTime

@Composable
fun DreamHeader(
    title: String,
    createdDate: String,
    createdTime: String,
    isLucid: Boolean,
    stripColor: Color,
    modifier: Modifier = Modifier
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

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DateText(text = createdDate)
                Ellipse(circleColor = CatppuccinColors.overlay0)
                DateText(text = createdTime)
            }
        }

        Icon(
            modifier = modifier.size(36.dp),
            imageVector = Icons.Rounded.Brightness7,
            contentDescription = "Lucid",
            tint = if (isLucid) LucidColor else Color.Transparent
        )
    }
}

@Composable
fun Ellipse(
    circleColor: Color
) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
        Canvas(contentDescription = "Ellipse", modifier = Modifier.size(4.dp)) {
            drawCircle(color = circleColor)
        }
    }
}

@Composable
fun DateText(
    text: String,
    dateColor: Color = CatppuccinColors.overlay0,
    dateSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        fontSize = dateSize,
        color = dateColor
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DreamHeaderPreview() {
    val now = LocalDateTime.now()

    val createdDate = now.month.toString() + " " + now.dayOfMonth + ", " + now.year
    val createdTime = now.hour.toString() + ":" + now.minute

    ApplicationTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(color = CatppuccinColors.base)
        ) {
            DreamHeader(
                title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                createdDate = createdDate.lowercase().replaceFirstChar { it.uppercase() },
                createdTime = createdTime,
                isLucid = true,
                stripColor = CatppuccinColors.yellow
            )
        }
    }
}
