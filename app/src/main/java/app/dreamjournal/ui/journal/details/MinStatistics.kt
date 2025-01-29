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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.ui.shared.rememberDateTimeFormatter
import app.dreamjournal.ui.theme.CatppuccinColors
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun MinStatistic(
    wordCount: Int,
    bedtime: LocalTime?,
    wakeUpTime: LocalTime?,
    formatter: DateTimeFormatter = rememberDateTimeFormatter("HH:mm"),
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
                contentDescription = "Count words",
                tint = contentColor
            )

            Text(
                text = "$wordCount words",
                style = textStyle,
                color = contentColor,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Hotel,
                contentDescription = "Dream time",
                tint = contentColor
            )

            val sleepTimeText = run {
                val unknown = "?"
                val first = if (bedtime == null) unknown else formatter.format(bedtime)
                val second = if (wakeUpTime == null) unknown else formatter.format(wakeUpTime)
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
