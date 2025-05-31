package com.alexyach.compose.groupsaa.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Home: Screen(ROUTE_HOME)
    object Meetings: Screen(ROUTE_MEETINGS)
    object GroupList: Screen(ROUTE_GROUP_LIST)
    object Group: Screen(ROUTE_GROUP)
    object News: Screen(ROUTE_NEWS)
    object Read: Screen(ROUTE_READ)

    private companion object {
        const val ROUTE_HOME = "home"

        const val ROUTE_MEETINGS = "meetings" // contains "GroupList" and "group"
        const val ROUTE_GROUP_LIST = "groupList"
        const val ROUTE_GROUP = "group"

        const val ROUTE_NEWS = "news"
        const val ROUTE_READ = "read"

    }

}