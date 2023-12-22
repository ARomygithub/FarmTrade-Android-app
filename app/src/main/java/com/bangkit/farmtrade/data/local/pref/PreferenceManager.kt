package com.bangkit.farmtrade.data.local.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun setToken(token : String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun setId(id : Int) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id.toString()
        }
    }

    suspend fun getId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[ID_KEY] ?: ""
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ID_KEY = stringPreferencesKey("id")
    }
}