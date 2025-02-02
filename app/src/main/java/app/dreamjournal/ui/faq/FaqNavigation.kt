package app.dreamjournal.ui.faq

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.dreamjournal.ui.DreamJournalNavigation

fun NavGraphBuilder.faqDestination(
    onBack: () -> Unit,
) {
    composable<DreamJournalNavigation.FaqRoute> {
        FaqScreen()
    }
}

fun NavController.navigateToFaq() {
    navigate(DreamJournalNavigation.FaqRoute)
}
