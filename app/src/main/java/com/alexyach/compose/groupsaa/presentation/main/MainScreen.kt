package com.alexyach.compose.groupsaa.presentation.main

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexyach.compose.groupsaa.presentation.navigation.rememberNavigationState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import com.alexyach.compose.groupsaa.presentation.navigation.AppNavGraph

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(
//        modifier = Modifier.fillMaxSize(),

        bottomBar = {

            val navBackStackEntry by navigationState.navHotController.currentBackStackEntryAsState()

            NavigationBar {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Meetings,
                    NavigationItem.News,
                    NavigationItem.Read
                )

                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )

                }

            }

        }
    ) { innerPadding ->

        val ip = innerPadding

        AppNavGraph(
            navHostController = navigationState.navHotController,
            homeScreenContent = { Text(text = "HOME") },
            groupListScreenContent = { Text(text = "GroupList") },
            groupScreenContent = { Text(text = "group") },
            newsScreenContent = { Text(text = "News") },
            readScreenContent = { Text(text = "Read") },
        )


    }
}