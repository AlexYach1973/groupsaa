package com.alexyach.compose.groupsaa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alexyach.compose.groupsaa.domain.entity.Group

class NavigationState(
    val navHotController: NavHostController
) {
    fun navigateTo(route: String) {
        navHotController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHotController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }

    fun navigateToGroup(group: Group) {
        navHotController.navigate(Screen.Group.getRouteWithArgs(group = group))
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) : NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}