package app.dreamjournal.ui.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
sealed class ApplicationNavigation {
    @Serializable
    data class DreamJournal(val searchQuery: String? = null) : ApplicationNavigation()

    @Serializable
    data object Calendar : ApplicationNavigation()

    @Serializable
    data object Statistics : ApplicationNavigation()

    @Serializable
    data object Tools : ApplicationNavigation()

    @Serializable
    data object Faq : ApplicationNavigation()

    @Serializable
    data object Settings : ApplicationNavigation()
}
