package app.dreamjournal.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import app.dreamjournal.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun rememberDateTimeFormatter(pattern: String): DateTimeFormatter {
    val locale = LocalConfiguration.current.locales[0]
    return remember(locale) { DateTimeFormatter.ofPattern(pattern, locale) }
}

@Composable
fun LocalTime.toText(): String {
    val pattern = stringResource(R.string.pattern_time_short)
    return rememberDateTimeFormatter(pattern).format(this)
}

@Composable
fun LocalDate.toShortText(): String {
    val pattern = stringResource(R.string.pattern_date_short)
    return rememberDateTimeFormatter(pattern).format(this)
}

@Composable
fun LocalDate.toLongText(): String {
    val pattern = stringResource(R.string.pattern_date_long)
    return rememberDateTimeFormatter(pattern).format(this)
}
