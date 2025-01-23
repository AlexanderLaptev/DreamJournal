package app.dreamjournal.ui.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
sealed class ApplicationNavigation {
    @Serializable
    data object DreamJournal : ApplicationNavigation()

    @Serializable
    data object Calendar : ApplicationNavigation()

    @Serializable
    data object Statistics : ApplicationNavigation()

    @Serializable
    data object Tools : ApplicationNavigation()

    @Serializable
    data object Wiki : ApplicationNavigation()

    @Serializable
    data object Settings : ApplicationNavigation()
}
