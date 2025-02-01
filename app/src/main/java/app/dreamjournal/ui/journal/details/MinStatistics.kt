package app.dreamjournal.ui.journal.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.util.toText
import java.time.LocalTime

@Composable
fun MinStatistic(
    wordCount: Int,
    bedtime: LocalTime?,
    wakeUpTime: LocalTime?,
) {
    val textStyle = MaterialTheme.typography.titleSmall
    val contentColor = CatppuccinColors.subtext0

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "",
                tint = contentColor,
            )

            Text(
                text = stringResource(R.string.label_word_count, wordCount),
                style = textStyle,
                color = contentColor,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (bedtime == null && wakeUpTime == null) return
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Hotel,
                contentDescription = "",
                tint = contentColor
            )

            val sleepTimeText = run {
                val unknown = stringResource(R.string.label_not_available)
                val first = bedtime?.toText() ?: unknown
                val second = wakeUpTime?.toText() ?: unknown
                "$first – $second"
            }
            Text(
                text = sleepTimeText,
                style = textStyle,
                color = contentColor
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    MinStatistic(
        wordCount = 284,
        LocalTime.of(23, 0),
        LocalTime.of(7, 0),
    )
}
