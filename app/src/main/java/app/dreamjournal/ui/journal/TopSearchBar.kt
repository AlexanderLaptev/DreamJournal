package app.dreamjournal.ui.journal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.dreamjournal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onCalendar: () -> Unit,
    onSettings: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        SearchBar(
            inputField = {
                val searching = expanded || query.isNotEmpty()

                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    expanded = expanded,
                    onExpandedChange = onExpandedChange,

                    onSearch = {
                        onExpandedChange(false)
                        onSearch(it)
                    },

                    placeholder = {
                        Text(
                            text = stringResource(R.string.journal_search_prompt),
                        )
                    },

                    leadingIcon = {
                        // Search/Back Button
                        IconButton(
                            onClick = {
                                if (searching) {
                                    onQueryChange("")
                                    onExpandedChange(false)
                                } else {
                                    onExpandedChange(true)
                                }
                            },
                        ) {
                            Crossfade(targetState = searching) {
                                if (it) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = stringResource(R.string.button_back_desc),
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = stringResource(R.string.button_search_desc),
                                    )
                                }
                            }
                        }
                    },

                    trailingIcon = {
                        Row {
                            // Calendar Button
                            AnimatedVisibility(
                                visible = !searching,
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                IconButton(onClick = onCalendar) {
                                    Icon(
                                        imageVector = Icons.Rounded.CalendarToday,
                                        contentDescription = stringResource(R.string.button_calendar_desc),
                                    )
                                }
                            }

                            // Settings/Clear Button
                            IconButton(
                                onClick = {
                                    if (expanded) {
                                        onQueryChange("")
                                    } else {
                                        if (!searching) onSettings()
                                    }
                                }
                            ) {
                                AnimatedVisibility(
                                    visible = !(!expanded && query.isNotEmpty()),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    Crossfade(targetState = searching) {
                                        if (it) {
                                            Icon(
                                                imageVector = Icons.Rounded.Clear,
                                                contentDescription = stringResource(R.string.button_clear_desc),
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Rounded.Settings,
                                                contentDescription = stringResource(R.string.button_settings_desc),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            },

            expanded = expanded,
            onExpandedChange = onExpandedChange,
        ) {}
    }
}
