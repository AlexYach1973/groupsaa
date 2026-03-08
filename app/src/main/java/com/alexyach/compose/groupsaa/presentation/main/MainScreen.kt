package com.alexyach.compose.groupsaa.presentation.main

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.alexyach.compose.groupsaa.presentation.read.ReadScreen
import kotlinx.serialization.Serializable



@Composable
fun MainScreen() {

    val homeKey = UnityKey //  ServiceKey // RecoveryKey // UnityKey
    val backStack = rememberNavBackStack(homeKey)

    Scaffold(
        bottomBar = {
            NavigationBar(
//                containerColor = Color(0x303E4759)
                containerColor = Color.Transparent

            ) {
                TOP_LEVEL_SCREEN.forEach { topLevelScreen ->

                    var isSelected = topLevelScreen == backStack.last()

                    /**
                     * При выделении подменю горела соответствующая иконка TOP_LEVEL_SCREEN
                     * Надо заполнить topLevelToSecondLevel
                     **/
                    if( !TOP_LEVEL_SCREEN.contains(backStack.last()) &&
                        !topLevelToSecondLevel[topLevelScreen].isNullOrEmpty()) {
                        isSelected = true
                    }

                    /* Old code*/
//                    if (topLevelScreen == UnityKey &&
//                        backStack.last().javaClass.toString().substringAfterLast(".") == "GroupDetails") {
//                        isSelected = true
//                    }

                    NavigationBarItem(
                        selected = isSelected,
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.secondary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.secondary,

                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            disabledIconColor = MaterialTheme.colorScheme.error,
                            disabledTextColor = MaterialTheme.colorScheme.error
                        ),

                        icon = {
                            Icon(
                                painter = painterResource(topLevelScreen.icon),
                                contentDescription = topLevelScreen.label.toString(),
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = {
                            backStack.clear()
                            backStack.add(homeKey)
                            if (topLevelScreen != homeKey) {
                                backStack.add(topLevelScreen)
                            }

                            /* Logs */
//                            backStack.forEach {
//                                Log.i("Logs", "backstack: ${it}")
//                            }
//                            Log.i("Logs", "----------------------------------------------------")

                        },
                        label = { Text(text = stringResource(topLevelScreen.label)) }
                    )
                } // end foreach()
            }


        }
    ) { paddingValues ->

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {

                /* Group List */
                entry<UnityKey> {
                    GroupListScreen(
                     paddingValues = paddingValues,
                        onGroupClickListener = {
                            backStack.add(GroupDetails(it))
                        }
                    )
                }

                entry<GroupDetails>(metadata = animationGroupDetails())
                {key->
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
                    ReadScreen(paddingValues = paddingValues)
//                    NewsScreen(paddingValues = paddingValues )
                }

            },
            /* Animation */
            transitionSpec = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(1000)
                ) togetherWith
                        slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(1000))

            },
            popTransitionSpec = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(1000)
                ) togetherWith slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(1000))
            },
            predictivePopTransitionSpec = {
//                 Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            }

        )

    }


}

/* Соеденяем TOP_LEVEL_SCREEN с подменю */
val topLevelToSecondLevel: Map<NavKey, List<String>> = mapOf(
    UnityKey to listOf("GroupDetails"),
    RecoveryKey to listOf(),
    ServiceKey to listOf()
)

private val TOP_LEVEL_SCREEN = listOf(UnityKey, RecoveryKey, ServiceKey)

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
    override val icon = R.drawable.health
    override val label = R.string.navigation3_item_recovery
}

@Serializable
private data object ServiceKey : TopLevelScreen {
    override val icon = R.drawable.book2
    override val label = R.string.navigation3_item_service
}




private fun animationGroupDetails(): Map<String, Any> {
    return NavDisplay.transitionSpec {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(1000)
        ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(1000)
                )
    } + NavDisplay.popTransitionSpec {
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(1000)
        ) togetherWith
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(1000)
                )
    } + NavDisplay.predictivePopTransitionSpec {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(1000)
        ) togetherWith
                slideOutHorizontally(targetOffsetX = { -it })
    }
}
