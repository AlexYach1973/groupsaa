package com.alexyach.compose.groupsaa.presentation.navigation

import android.net.Uri
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {
    object Home: Screen(ROUTE_HOME)
    object Meetings: Screen(ROUTE_MEETINGS)
    object GroupList: Screen(ROUTE_GROUP_LIST)

    object Group: Screen(ROUTE_GROUP) {
        private const val ROUTE_FOR_ARGS = "group"

        fun getRouteWithArgs(group: com.alexyach.compose.groupsaa.domain.model.Group) : String {
            val groupJson = Gson().toJson(group)
            return "$ROUTE_FOR_ARGS/${groupJson.encode()}"
        }
    }

    object News: Screen(ROUTE_NEWS)
    object Read: Screen(ROUTE_READ)

    companion object {

        const val KEY_GROUP = "key_group"

        const val ROUTE_HOME = "home"

        const val ROUTE_MEETINGS = "meetings" // contains "GroupList" and "group"
        const val ROUTE_GROUP_LIST = "groupList"
        const val ROUTE_GROUP = "group/{$KEY_GROUP}"

        const val ROUTE_NEWS = "news"
        const val ROUTE_READ = "read"

    }

}

fun String.encode(): String {
    return Uri.encode(this)
}