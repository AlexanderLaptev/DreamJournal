package app.dreamjournal.ui.journal.calendar

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils.clamp
import app.dreamjournal.ui.theme.DreamJournalTheme
import app.dreamjournal.ui.theme.CatppuccinColorScheme
import app.dreamjournal.ui.theme.CatppuccinColors
import app.dreamjournal.ui.util.blend

val CALENDAR_DAY_SIZE = 48.dp

const val CALENDAR_LEVELS = 4

fun getCalendarLevelColor(level: Int, scheme: CatppuccinColorScheme): Color {
    require(level in 0..<CALENDAR_LEVELS) { "Unsupported calendar level: $level" }

    val maxBlendFactor = 0.6f
    val step = maxBlendFactor / (CALENDAR_LEVELS - 1)
    return scheme.crust.blend(scheme.mauve, step * level)
}

@Suppress("NAME_SHADOWING")
@Composable
fun CalendarDay(
    day: Int,
    selected: Boolean = false,
    level: Int = 0,
) {
    val level = clamp(level, 0, 3)

    val circleSize = 32.dp
    val circleColor = CatppuccinColors.text
    val shape = MaterialTheme.shapes.small
    val textStyle = MaterialTheme.typography.labelLarge

    val boxColor = getCalendarLevelColor(level, CatppuccinColors)

    var boxModifier = Modifier
        .background(color = boxColor, shape = shape)
        .size(CALENDAR_DAY_SIZE)
    if (level == 0) {
        boxModifier = boxModifier.border(
            width = 2.dp,
            color = CatppuccinColors.surface0,
            shape = shape
        )
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Canvas(Modifier.size(circleSize)) {
                drawCircle(circleColor)
            }
        }

        Text(
            text = day.toString(),
            style = textStyle,
            color = if (selected) CatppuccinColors.base else CatppuccinColors.text,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewNormal() {
    val day = 18
    DreamJournalTheme {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                repeat(CALENDAR_LEVELS) {
                    CalendarDay(day = day, level = it, selected = false)
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSelected() {
    val day = 18
    DreamJournalTheme {
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                repeat(CALENDAR_LEVELS) {
                    CalendarDay(day = day, level = it, selected = true)
                }
            }
        }
    }
}
