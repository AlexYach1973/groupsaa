package com.alexyach.compose.groupsaa

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.alexyach.compose.groupsaa.data.db.GroupDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

//    val database: GroupDatabase by lazy { GroupDatabase.getDatabase(this) }
//    val groupDao by lazy { database.groupDao() }

//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsData")

}