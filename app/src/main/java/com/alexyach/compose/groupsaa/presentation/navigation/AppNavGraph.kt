package com.alexyach.compose.groupsaa.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    groupListScreenContent: @Composable () -> Unit,
    groupScreenContent: @Composable () -> Unit,
    newsScreenContent: @Composable () -> Unit,
    readScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            homeScreenContent()
        }

        meetingsScreenNavGraph(
            groupListScreenContent = groupListScreenContent,
            groupScreenContent = groupScreenContent
        )

        composable(Screen.News.route) {
            newsScreenContent()
        }

        composable(Screen.Read.route) {
            readScreenContent()
        }

    }

}