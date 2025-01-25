package app.dreamjournal.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.dreamjournal.ui.theme.ThemePreference
import kotlinx.coroutines.flow.first

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object KEYS {
    val THEME_KEY = stringPreferencesKey(name = "theme_key")
}

suspend fun saveThemeToDataStore(context: Context, themePreference: ThemePreference) {
    context.settingsDataStore.edit {
        it[KEYS.THEME_KEY] = themePreference.name
    }
}

suspend fun loadThemeFromDataStore(context: Context): ThemePreference {
    val themePreferences = context.settingsDataStore.data.first()
    return when (themePreferences[KEYS.THEME_KEY]) {
        ThemePreference.Light.name -> ThemePreference.Light
        ThemePreference.Dark.name -> ThemePreference.Dark
        else -> ThemePreference.System
    }
}
