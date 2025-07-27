package com.alexyach.compose.groupsaa.presentation.main

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexyach.compose.groupsaa.presentation.group.GroupScreen
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListScreen
import com.alexyach.compose.groupsaa.presentation.home.HomeScreen
import com.alexyach.compose.groupsaa.presentation.navigation.AppNavGraph
import com.alexyach.compose.groupsaa.presentation.navigation.rememberNavigationState
import com.alexyach.compose.groupsaa.presentation.news.NewsScreen
import com.alexyach.compose.groupsaa.presentation.read.ReadScreen

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),

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
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary
                        )
                    )

                }

            }

        }
    ) { innerPadding ->

        val ip = innerPadding

        AppNavGraph(
            navHostController = navigationState.navHotController,

            homeScreenContent = {
                HomeScreen(
                    paddingValues = innerPadding,
                    onReadClickListener = {
                            navigationState.navigateTo(NavigationItem.Read.screen.route)
                        }
                )
            },

            groupListScreenContent = {
                GroupListScreen(
                    paddingValues = innerPadding,
                    onGroupClickListener = { group ->
                        navigationState.navigateToGroup(group = group)
                    }
                )
            },

            groupScreenContent = { group ->
                GroupScreen(
                    group = group,
                    onBackPress = {
                        navigationState.navHotController.popBackStack()
                    }
                )
            },
            newsScreenContent = {
                NewsScreen(
                    paddingValues = innerPadding
                )
            },
            readScreenContent = {
                ReadScreen(
                    paddingValues = innerPadding
                )
            },
        )


    }
}