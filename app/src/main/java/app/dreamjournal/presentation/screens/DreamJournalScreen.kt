package app.dreamjournal.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DreamJournalScreen() {
    // The screen is plug
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "DreamJournal Screen")
    }
}

@Preview
@Composable
fun DreamJournalScreenPreview() {
    DreamJournalScreen()
}
