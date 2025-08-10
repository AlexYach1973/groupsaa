package com.alexyach.compose.groupsaa.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsData")


class DataStoreManager(private val context: Context) {

    /* Save DAte */
    suspend fun saveSelectedData(values: List<Int>){
        context.dataStore.edit { preferences ->
            preferences[KEY_YEAR] = values[0]
            preferences[KEY_MONTH] = values[1]
            preferences[KEY_DAY] = values[2]

//            Log.d("Logs", "DataStoreManager saveSelectedData: $values")
        }
    }

    /* Load Date */
    val loadYear : Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_YEAR]?: 0
        }

    val loadMonth : Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_MONTH]?: 0
        }

    val loadDay : Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_DAY]?: 0
        }

    /* SelectedDate */
    suspend fun saveSelectedDataList(list: List<Int>) {
        val serialized = list.joinToString(",") { it.toString() }
        context.dataStore.edit { prefs ->
            prefs[SELECTED_DATE_IST_KEY] = serialized
        }
    }

    suspend fun readSelectedDataList(): List<Int> {
        val prefs = context.dataStore.data.first()
        val serialized = prefs[SELECTED_DATE_IST_KEY] ?: return emptyList()
        return serialized.split(",").map { it.toInt() }
    }



    /* Preferences Visible Prayers */
    suspend fun savePrefVisiblePrayerList(list: List<Boolean>) {
        val serialized = list.joinToString(",") { it.toString() }
        context.dataStore.edit { prefs ->
            prefs[PREF_PRAYERS_LIST_KEY] = serialized
        }
    }

    suspend fun readPrefVisiblePrayerList(): List<Boolean> {
        val prefs = context.dataStore.data.first()
        val serialized = prefs[PREF_PRAYERS_LIST_KEY] ?: return emptyList()
        return serialized.split(",").map { it.toBoolean() }
    }


    companion object {
        private val KEY_YEAR = intPreferencesKey("user_year")
        private val KEY_MONTH = intPreferencesKey("user_month")
        private val KEY_DAY = intPreferencesKey("user_day")

        /* preferences show Prayers */
        val PREF_PRAYERS_LIST_KEY = stringPreferencesKey("pref_prayers_list")
        val SELECTED_DATE_IST_KEY = stringPreferencesKey("selected_date_list")


    }


}
