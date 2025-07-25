package com.alexyach.compose.groupsaa.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alexyach.compose.groupsaa.domain.model.Prayers
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


    suspend fun saveIsVisiblePrayer(keyMap: Prayers, value: Boolean) {

        val key: Preferences.Key<Boolean> = prayerKeyMap[keyMap]!!

        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }


    /* *** Load preferences Show Prayer *** */
    val prefMorningPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.MorningPrayer]!!]?: true
        }

    val prefEveningPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.EveningPrayer]!!]?: true
        }
    val prefDelegationPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.DelegationPrayer]!!]?: true
        }
    val prefPeaceOfMindPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.PeaceOfMindPrayer]!!]?: true
        }
    val prefResentmentPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.ResentmentPrayer]!!]?: true
        }
    val prefFearPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.FearPrayer]!!]?: true
        }
    val prefStepTenPrayer: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[prayerKeyMap[Prayers.StepTenPrayer]!!]?: true
        }


    companion object {
        private val KEY_YEAR = intPreferencesKey("user_year")
        private val KEY_MONTH = intPreferencesKey("user_month")
        private val KEY_DAY = intPreferencesKey("user_day")

        /* preferences show Prayers */
        val prayerKeyMap = mapOf<Prayers,  Preferences.Key<Boolean>>(
            Prayers.MorningPrayer to booleanPreferencesKey("morning_prayer"),
            Prayers.EveningPrayer to booleanPreferencesKey("evening_prayer"),
            Prayers.DelegationPrayer to booleanPreferencesKey("delegation_prayer"),
            Prayers.PeaceOfMindPrayer to booleanPreferencesKey("peace_of_mind_prayer"),
            Prayers.ResentmentPrayer to booleanPreferencesKey("resentment_prayer"),
            Prayers.FearPrayer to booleanPreferencesKey("fear_prayer"),
            Prayers.StepTenPrayer to booleanPreferencesKey("step_ten_prayer")
        )

    }


}
