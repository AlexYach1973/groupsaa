package com.alexyach.compose.groupsaa.presentation.main

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreen
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListScreen
import com.alexyach.compose.groupsaa.presentation.home.HomeScreen
import com.alexyach.compose.groupsaa.presentation.news.NewsScreen
import kotlinx.serialization.Serializable


private sealed interface TopLevelScreen : NavKey {
    val icon: Int
    val label: Int
}

@Serializable
private data object UnityKey : TopLevelScreen {
    override val icon = R.drawable.group2
    override val label = R.string.navigation3_item_unity
}

@Serializable
private data class GroupDetails(val group: Group) : NavKey

@Serializable
private data object RecoveryKey : TopLevelScreen {
    override val icon = R.drawable.self_improvement
    override val label = R.string.navigation3_item_recovery
}

@Serializable
private data object ServiceKey : TopLevelScreen {
    override val icon = R.drawable.seminar
    override val label = R.string.navigation3_item_service
}

private val TOP_LEVEL_SCREEN = listOf(UnityKey, RecoveryKey, ServiceKey)

@Composable
fun MainScreen() {


    val homeKey = UnityKey
    val backStack = rememberNavBackStack(homeKey)

    Scaffold(
        bottomBar = {
            NavigationBar {
            TOP_LEVEL_SCREEN.forEach { topLevelScreen ->

                val isSelected = topLevelScreen == backStack.last()

                NavigationBarItem(
                    selected = isSelected,

                    icon = {
                        Icon(
                            painter = painterResource(topLevelScreen.icon),
                            contentDescription = topLevelScreen.label.toString(),
                            modifier = Modifier.size(32.dp)
                        )
                    },
                    onClick = {
                        backStack.clear()
                        backStack.add(homeKey)
                        if (topLevelScreen != homeKey) {
                            backStack.add(topLevelScreen)
                        }
                    },
                    label = { Text(text = stringResource(topLevelScreen.label)) }
                )

            }
        }
        }
    ) {paddingValues ->

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {

                /* Group List */
                entry<UnityKey> {
                    GroupListScreen(
                     paddingValues = paddingValues,
                        onGroupClickListener = { backStack.add(GroupDetails(it)) }
                    )
                }

                entry<GroupDetails> {key->
                    GroupScreen(
                        group = key.group,
                        onBackPress = { backStack.removeLastOrNull() }
                        )
                }

                entry<RecoveryKey> {
                    HomeScreen(
                        paddingValues = paddingValues,
                        onReadClickListener = {}
                    )
                }

                entry<ServiceKey> {
                    NewsScreen(
                        paddingValues = paddingValues
                    )
                }




            }
        )

    }


}

/*

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

 */