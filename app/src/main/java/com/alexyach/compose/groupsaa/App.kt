package com.alexyach.compose.groupsaa

import android.app.Application
import com.alexyach.compose.groupsaa.data.db.GroupDatabase

class App : Application() {

    val database: GroupDatabase by lazy { GroupDatabase.getDatabase(this) }
    val groupDao by lazy { database.groupDao() }

}