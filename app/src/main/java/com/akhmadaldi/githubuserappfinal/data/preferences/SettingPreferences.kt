package com.akhmadaldi.githubuserappfinal.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val keyThemeApp = booleanPreferencesKey("theme_mode")

    fun getThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preferences -> preferences[keyThemeApp] ?: false }
    }

    suspend fun setThemeMode(isNightMode: Boolean) {
        dataStore.edit { preferences -> preferences [keyThemeApp] = isNightMode }
    }

    companion object {
        @Volatile
        private var instance: SettingPreferences? = null

        fun newInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return instance ?: synchronized(this) {
                val ins = SettingPreferences(dataStore)
                instance = ins
                ins
            }
        }
    }
}