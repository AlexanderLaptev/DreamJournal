package app.dreamjournal.ui.journal.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.dreamjournal.R
import app.dreamjournal.ui.theme.CatppuccinColors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

private const val DAYS_IN_WEEK = 7

private val NAMES = listOf(
    R.string.monday_short,
    R.string.tuesday_short,
    R.string.wednesday_short,
    R.string.thursday_short,
    R.string.friday_short,
    R.string.saturday_short,
    R.string.sunday_short,
)

private const val FULL_COUNT = 5 * DAYS_IN_WEEK

@Composable
fun Calendar(
    yearMonth: YearMonth = YearMonth.now(),
    levels: IntArray = intArrayOf(),
    selectedIndex: Int = -1,
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
) {
    val spacing = 8.dp
    val totalDays = yearMonth.lengthOfMonth()
    val shift = firstDayOfWeek.value - DayOfWeek.MONDAY.value

    var lucidFilterEnabled by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1.0f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = currentYearMonthToString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = CatppuccinColors.text,
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.calendar_button_edit_date),
                        tint = CatppuccinColors.text,
                    )
                }
            }

            val labelColor = if (lucidFilterEnabled) {
                CatppuccinColors.base
            } else CatppuccinColors.text
            FilterChip(
                selected = lucidFilterEnabled,
                onClick = { lucidFilterEnabled = !lucidFilterEnabled },
                label = {
                    Text(
                        text = stringResource(R.string.calendar_filter_lucid),
                        color = labelColor,
                    )
                },
                leadingIcon = {
                    if (lucidFilterEnabled) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "",
                            tint = CatppuccinColors.base,
                        )
                    }
                }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(DAYS_IN_WEEK),
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalArrangement = Arrangement.spacedBy(spacing),
        ) {
            items(DAYS_IN_WEEK) {
                val index = (it + shift) % DAYS_IN_WEEK
                val text = stringResource(NAMES[index]).uppercase()

                Text(
                    text = text,
                    color = CatppuccinColors.overlay0,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                )
            }

            val spacerCount = let {
                val firstDay = yearMonth.atDay(1).dayOfWeek
                val diff = firstDay.value - firstDayOfWeek.value
                Math.floorMod(diff, DAYS_IN_WEEK)
            }

            items(spacerCount) {
                Spacer(Modifier.size(CALENDAR_DAY_SIZE))
            }

            items(totalDays) {
                CalendarDay(
                    day = it + 1,
                    selected = it == selectedIndex,
                    level = if (it < levels.size) levels[it] else 0,
                )
            }

            // Adding spacers to ensure the calendar always displays all 6 rows
            val totalCells = spacerCount + totalDays
            if (totalCells <= FULL_COUNT) {
                val remaining = FULL_COUNT - totalCells + 1
                items(remaining) {
                    Spacer(Modifier.size(CALENDAR_DAY_SIZE))
                }
            }
        }

        LevelsPreview()
    }
}

@Composable
private fun LevelsPreview() {
    val boxSize = 20.dp

    Row(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.calendar_less),
            style = MaterialTheme.typography.labelSmall,
            color = CatppuccinColors.overlay0,
        )

        repeat(CALENDAR_LEVELS) { level ->
            var modifier = Modifier
                .size(boxSize)
                .background(
                    color = getCalendarLevelColor(level, CatppuccinColors),
                    shape = MaterialTheme.shapes.extraSmall
                )
            if (level == 0) modifier = modifier.border(
                width = 2.dp,
                color = CatppuccinColors.surface0,
                shape = MaterialTheme.shapes.extraSmall
            )
            Box(modifier)
        }

        Text(
            text = stringResource(R.string.calendar_more),
            style = MaterialTheme.typography.labelSmall,
            color = CatppuccinColors.overlay0,
        )
    }
}

@Composable
fun currentYearMonthToString(): String {
    val now = LocalDate.now()
    val monthResId = when (now.month!!) {
        Month.JANUARY -> R.string.month_january
        Month.FEBRUARY -> R.string.month_february
        Month.MARCH -> R.string.month_march
        Month.APRIL -> R.string.month_april
        Month.MAY -> R.string.month_may
        Month.JUNE -> R.string.month_june
        Month.JULY -> R.string.month_july
        Month.AUGUST -> R.string.month_august
        Month.SEPTEMBER -> R.string.month_september
        Month.OCTOBER -> R.string.month_october
        Month.NOVEMBER -> R.string.month_november
        Month.DECEMBER -> R.string.month_december
    }
    return stringResource(R.string.calendar_pattern, stringResource(monthResId), now.year)
}

private val PREVIEW_YEAR_MONTH = YearMonth.of(2024, Month.FEBRUARY)
private val PREVIEW_LEVELS = intArrayOf(
    0, 0, 1, 2, 0, 1, 3,
    0, 1, 2, 3, 1, 2, 1,
    2, 0, 2, 3, 2, 0, 1,
    3, 2, 3, 0, 1, 0, 2,
    2, 0, 3
)
private const val PREVIEW_SELECTED_DAY = 18

@Preview(showBackground = false)
@Composable
private fun PreviewMonday() {
    Calendar(
        yearMonth = PREVIEW_YEAR_MONTH,
        levels = PREVIEW_LEVELS,
        selectedIndex = PREVIEW_SELECTED_DAY,
        firstDayOfWeek = DayOfWeek.MONDAY,
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewSunday() {
    Calendar(
        yearMonth = PREVIEW_YEAR_MONTH,
        levels = PREVIEW_LEVELS,
        selectedIndex = PREVIEW_SELECTED_DAY,
        firstDayOfWeek = DayOfWeek.SUNDAY,
    )
}
