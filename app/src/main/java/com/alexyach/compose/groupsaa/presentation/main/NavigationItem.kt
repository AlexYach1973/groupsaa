package com.alexyach.compose.groupsaa.presentation.main

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    @DrawableRes
    val icon: Int
) {
    object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = R.drawable.home
    )

    object Meetings: NavigationItem(
        screen = Screen.Meetings,
        titleResId = R.string.navigation_item_meeting,
        icon = R.drawable.group2
    )

    object News: NavigationItem(
        screen = Screen.News,
        titleResId = R.string.navigation_item_news,
        icon = R.drawable.news
    )

    object Read: NavigationItem(
        screen = Screen.Read,
        titleResId = R.string.navigation_item_read,
        icon = R.drawable.book2
    )
}