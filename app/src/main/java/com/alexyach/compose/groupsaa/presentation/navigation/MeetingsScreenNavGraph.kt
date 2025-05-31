package com.alexyach.compose.groupsaa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.meetingsScreenNavGraph(
    groupListScreenContent: @Composable () -> Unit,
    groupScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.GroupList.route,
        route = Screen.Meetings.route
    ) {
        composable(Screen.GroupList.route) {
            groupListScreenContent()
        }
        composable(Screen.Group.route) {
            groupScreenContent()
        }

    }
}