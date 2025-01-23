package app.dreamjournal.ui.journal

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.dreamjournal.ui.navigation.ApplicationNavigation
import app.dreamjournal.ui.theme.ApplicationTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamJournalScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SearchBar(
                    modifier = Modifier.align(Alignment.Center),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onQueryChange = { query = it },
                            placeholder = { Text("Search your dreams") },
                            onSearch = {
                                Toast.makeText(
                                    context,
                                    "Searching \"$query\"",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                expanded = false
                            },
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            leadingIcon = {
                                IconButton(
                                    onClick = {},
                                ) {
                                    val icon = if (expanded || query.isNotBlank()) {
                                        Icons.AutoMirrored.Rounded.ArrowBack
                                    } else Icons.Rounded.Search

                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "",
                                    )
                                }
                            },
                            trailingIcon = {
                                val icon = when (expanded) {
                                    true -> Icons.Rounded.Close
                                    false -> Icons.Rounded.CalendarToday
                                }
                                IconButton(
                                    onClick = {
                                        if (!expanded) {
                                            navController.navigate(ApplicationNavigation.Calendar)
                                        }
                                    },
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = "",
                                    )
                                }
                            },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) { }
            }
        }
    ) {
    }
}

@Preview
@Composable
fun DreamJournalScreenPreview() {
    ApplicationTheme {
        DreamJournalScreen(rememberNavController())
    }
}
