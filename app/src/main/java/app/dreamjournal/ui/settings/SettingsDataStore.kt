package app.dreamjournal.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.dreamjournal.ui.theme.Theme
import kotlinx.coroutines.flow.first

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object KEYS {
    val THEME_KEY = stringPreferencesKey(name = "theme_key")
}

suspend fun saveThemeToDataStore(context: Context, theme: Theme) {
    context.settingsDataStore.edit {
        it[KEYS.THEME_KEY] = theme.name
    }
}

suspend fun loadThemeFromDataStore(context: Context): Theme {
    val themePreferences = context.settingsDataStore.data.first()
    return when (themePreferences[KEYS.THEME_KEY]) {
        Theme.Light.name -> Theme.Light
        Theme.Dark.name -> Theme.Dark
        else -> Theme.System
    }
}
