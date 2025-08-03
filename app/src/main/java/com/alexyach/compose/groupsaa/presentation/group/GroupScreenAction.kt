package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

sealed class GroupScreenAction {

    data class CallGroup(val context:Context, val phoneNumber: String) : GroupScreenAction()
    data class GroupMap(val context: Context, val addressForMap: String) : GroupScreenAction()

    /* ************** Launchers **************** */
    data class LaunchTelegram(
        val context: Context,
        val uri: String,
        val telegramLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        val playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) : GroupScreenAction()

    data class LaunchSkype(
        val context: Context,
        val uri: String,
        val skypeLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        val playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) : GroupScreenAction()

    data class LaunchZoom(
        val context: Context,
        val uri: String,
        val zoomLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        val playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) : GroupScreenAction()

}