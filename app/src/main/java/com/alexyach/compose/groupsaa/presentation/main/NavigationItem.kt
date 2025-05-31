package com.alexyach.compose.groupsaa.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Default.Home
    )

    object Meetings: NavigationItem(
        screen = Screen.Meetings,
        titleResId = R.string.navigation_item_meeting,
        icon = Icons.Default.DateRange
    )

    object News: NavigationItem(
        screen = Screen.News,
        titleResId = R.string.navigation_item_news,
        icon = Icons.Outlined.Email
    )

    object Read: NavigationItem(
        screen = Screen.Read,
        titleResId = R.string.navigation_item_read,
        icon = Icons.AutoMirrored.Outlined.List
    )
}