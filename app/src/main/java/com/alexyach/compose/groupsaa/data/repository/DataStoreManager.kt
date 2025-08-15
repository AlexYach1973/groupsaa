package com.alexyach.compose.groupsaa.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


    /* SelectedDate */
    suspend fun saveSelectedDataList(list: List<Int>) {
        val serialized = list.joinToString(",") { it.toString() }
        dataStore.edit { prefs ->
            prefs[SELECTED_DATE_LIST_KEY] = serialized
        }
    }

    suspend fun readSelectedDataList(): List<Int> {
        val prefs = dataStore.data.first()
        val serialized = prefs[SELECTED_DATE_LIST_KEY] ?: return emptyList()
        return serialized.split(",").map { it.toInt() }
    }



    /* Preferences Visible Prayers */
    suspend fun savePrefVisiblePrayerList(list: List<Boolean>) {
        val serialized = list.joinToString(",") { it.toString() }
        dataStore.edit { prefs ->
            prefs[PREF_PRAYERS_LIST_KEY] = serialized
        }
    }

    suspend fun readPrefVisiblePrayerList(): List<Boolean> {
        val prefs = dataStore.data.first()
        val serialized = prefs[PREF_PRAYERS_LIST_KEY] ?: return emptyList()
        return serialized.split(",").map { it.toBoolean() }
    }


    companion object {

        /* preferences show Prayers */
        val PREF_PRAYERS_LIST_KEY = stringPreferencesKey("pref_prayers_list")
        val SELECTED_DATE_LIST_KEY = stringPreferencesKey("selected_date_list")


    }


}
