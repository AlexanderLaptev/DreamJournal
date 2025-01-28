package app.dreamjournal.ui.journal.details

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
import app.dreamjournal.ui.theme.CatppuccinColors

@Composable
fun MinStatistic(
    totalWords: Int,
    sleepingTime: String
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
                text = "$totalWords words",
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

            Text(
                text = sleepingTime,
                style = textStyle,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
fun MinStatisticPreview() {
    MinStatistic(totalWords = 284, sleepingTime = "23:00 - 07:00")
}
