package com.alexyach.compose.groupsaa.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
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

    /* *** Save preferences Show Prayer *** */
    suspend fun saveMorningPrayer(value: Boolean) {
        context.dataStore.edit {preferences ->
            preferences[KEY_MORNING_PRAYER] = value
        }
    }
    suspend fun saveEveningPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_EVENING_PRAYER] = value
        }
    }
    suspend fun saveDelegationPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DELEGATION_PRAYER] = value
        }
    }
    suspend fun savePeaceOfMindPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_PEACE_OF_MIND_PRAYER] = value
        }
    }
    suspend fun saveResentmentPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_RESENTMENT_PRAYER] = value
        }
    }
    suspend fun saveFearPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_FEAR_PRAYER] = value
        }
    }
    suspend fun saveStepTenPrayer(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_STEP_TEN_PRAYER] = value
        }
    }

    /* *** Load preferences Show Prayer *** */
    val prefMorningPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_MORNING_PRAYER]?: true
        }

    val prefEveningPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_EVENING_PRAYER]?: true
        }
    val prefDelegationPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_DELEGATION_PRAYER]?: true
        }
    val prefPeaceOfMindPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_PEACE_OF_MIND_PRAYER]?: true
        }
    val prefResentmentPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_RESENTMENT_PRAYER]?: true
        }
    val prefFearPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_FEAR_PRAYER]?: true
        }
    val prefStepTenPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_STEP_TEN_PRAYER]?: true
        }


    companion object {
        private val KEY_YEAR = intPreferencesKey("user_year")
        private val KEY_MONTH = intPreferencesKey("user_month")
        private val KEY_DAY = intPreferencesKey("user_day")

        /* preferences show Prayers */
        private val KEY_MORNING_PRAYER = booleanPreferencesKey("morning_prayer")
        private val KEY_EVENING_PRAYER = booleanPreferencesKey("evening_prayer")
        private val KEY_DELEGATION_PRAYER = booleanPreferencesKey("delegation_prayer")
        private val KEY_PEACE_OF_MIND_PRAYER = booleanPreferencesKey("peace_of_mind_prayer")
        private val KEY_RESENTMENT_PRAYER = booleanPreferencesKey("resentment_prayer")
        private val KEY_FEAR_PRAYER = booleanPreferencesKey("fear_prayer")
        private val KEY_STEP_TEN_PRAYER = booleanPreferencesKey("step_ten_prayer")



    }


}
