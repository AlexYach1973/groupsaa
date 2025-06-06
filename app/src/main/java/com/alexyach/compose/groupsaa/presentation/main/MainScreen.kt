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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import com.alexyach.compose.groupsaa.domain.entity.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreen
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListScreen
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
                            Icon(painterResource(item.icon), contentDescription = null)
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

            groupListScreenContent = {
                GroupListScreen(
                    paddingValues = innerPadding,
                    onGroupClickListener = {group ->
                        navigationState.navigateToGroup(group = group)
                    }
                )
            },

            groupScreenContent = {group ->
                GroupScreen(
                    group = group,
                    onBackPress = {
                        navigationState.navHotController.popBackStack()
                    }
                )
            },
            newsScreenContent = { Text(text = "News") },
            readScreenContent = { Text(text = "Read") },
        )


    }
}