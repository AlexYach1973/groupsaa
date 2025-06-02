package com.alexyach.compose.groupsaa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexyach.compose.groupsaa.domain.entity.Group

fun NavGraphBuilder.meetingsScreenNavGraph(
    groupListScreenContent: @Composable () -> Unit,
    groupScreenContent: @Composable (group: Group) -> Unit
) {
    navigation(
        startDestination = Screen.GroupList.route,
        route = Screen.Meetings.route
    ) {
        composable(Screen.GroupList.route) {
            groupListScreenContent()
        }
        composable(Screen.Group.route) {// {group_name}
            val groupName= it.arguments?.getString(Screen.KEY_GROUP_NAME) ?: "no"

            groupScreenContent(Group(name = groupName,"","","","",""))
        }

    }
}