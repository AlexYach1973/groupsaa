package com.alexyach.compose.groupsaa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alexyach.compose.groupsaa.domain.model.Group
import com.google.gson.Gson

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
        composable(
            route = Screen.Group.route,
            arguments = listOf(
                navArgument(Screen.KEY_GROUP) {
                    type = NavType.StringType
                }
            )
        ) {
            val groupJson= it.arguments?.getString(Screen.KEY_GROUP) ?: "no Object"
            val group = Gson().fromJson(groupJson, Group::class.java)
            groupScreenContent(group)
        }

    }
}