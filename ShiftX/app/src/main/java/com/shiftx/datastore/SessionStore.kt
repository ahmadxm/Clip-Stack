package com.shiftx.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "shiftx_prefs")

@Singleton
class SessionStore @Inject constructor(@ApplicationContext private val context: Context) {
    object Keys {
        val LOGGED_IN_EMPLOYEE_ID = stringPreferencesKey("logged_in_employee_id")
        val ADMIN_LOGGED_IN = booleanPreferencesKey("admin_logged_in")
        val THEME = stringPreferencesKey("theme") // light/dark/system
        val TIME_FORMAT = stringPreferencesKey("time_format") // 12/24
        val SHIFT_DISPLAY = stringPreferencesKey("shift_display") // full/abbr
    }

    val loggedInEmployeeIdFlow: Flow<String?> = context.dataStore.data.map { it[Keys.LOGGED_IN_EMPLOYEE_ID] }
    val adminLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.ADMIN_LOGGED_IN] ?: false }

    suspend fun setLoggedInEmployee(id: String?) {
        context.dataStore.edit { prefs ->
            if (id == null) prefs.remove(Keys.LOGGED_IN_EMPLOYEE_ID) else prefs[Keys.LOGGED_IN_EMPLOYEE_ID] = id
        }
    }

    suspend fun setAdminLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { it[Keys.ADMIN_LOGGED_IN] = loggedIn }
    }

    val themeFlow: Flow<String> = context.dataStore.data.map { it[Keys.THEME] ?: "system" }
    val timeFormatFlow: Flow<String> = context.dataStore.data.map { it[Keys.TIME_FORMAT] ?: "24" }
    val shiftDisplayFlow: Flow<String> = context.dataStore.data.map { it[Keys.SHIFT_DISPLAY] ?: "full" }

    suspend fun setTheme(value: String) { context.dataStore.edit { it[Keys.THEME] = value } }
    suspend fun setTimeFormat(value: String) { context.dataStore.edit { it[Keys.TIME_FORMAT] = value } }
    suspend fun setShiftDisplay(value: String) { context.dataStore.edit { it[Keys.SHIFT_DISPLAY] = value } }
}