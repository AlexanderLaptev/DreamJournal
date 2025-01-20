package app.dreamjournal.ui.calendar

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils.clamp
import app.dreamjournal.ui.theme.calendar_level0
import app.dreamjournal.ui.theme.calendar_level1
import app.dreamjournal.ui.theme.calendar_level2
import app.dreamjournal.ui.theme.calendar_level3
import app.dreamjournal.ui.theme.mocha_base
import app.dreamjournal.ui.theme.mocha_surface0
import app.dreamjournal.ui.theme.mocha_text

val CALENDAR_DAY_SIZE = 48.dp

private val LEVEL_COLORS = listOf(
    calendar_level0,
    calendar_level1,
    calendar_level2,
    calendar_level3,
)

@Suppress("NAME_SHADOWING")
@Composable
fun CalendarDay(
    day: Int,
    selected: Boolean = false,
    level: Int = 0,
) {
    val level = clamp(level, 0, 3)
    val circleSize = 32.dp

    val shape = MaterialTheme.shapes.small
    val textStyle = MaterialTheme.typography.labelLarge

    val boxColor = LEVEL_COLORS[level]
    var boxModifier = Modifier
        .background(color = boxColor, shape = shape)
        .size(CALENDAR_DAY_SIZE)
    if (level == 0) {
        boxModifier = boxModifier.border(
            width = 2.dp,
            color = mocha_surface0,
            shape = shape
        )
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Canvas(Modifier.size(circleSize)) {
                drawCircle(mocha_text)
            }
        }

        Text(
            text = day.toString(),
            style = textStyle,
            color = if (selected) mocha_base else mocha_text,
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewNormal() {
    val day = 18
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(LEVEL_COLORS.size) {
                CalendarDay(day = day, level = it, selected = false)
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewSelected() {
    val day = 18
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(LEVEL_COLORS.size) {
                CalendarDay(day = day, level = it, selected = true)
            }
        }
    }
}
