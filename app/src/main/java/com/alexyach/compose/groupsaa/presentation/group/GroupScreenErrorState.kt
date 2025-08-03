package com.alexyach.compose.groupsaa.presentation.group

sealed class GroupScreenErrorState {
    object NoError : GroupScreenErrorState()
    object SkypeError : GroupScreenErrorState()
    object TelegramError : GroupScreenErrorState()
    object ZoomError : GroupScreenErrorState()
}