package app.dreamjournal.ui.statistics

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.dreamjournal.ui.theme.DreamJournalTheme

@Composable
fun StatisticsScreen(
    onBack: () -> Unit = {}
) {
    BackHandler(onBack = onBack)

    // This screen is a plug
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Statistics Screen")
    }
}

@Preview
@Composable
private fun Preview() {
    DreamJournalTheme {
        StatisticsScreen()
    }
}
