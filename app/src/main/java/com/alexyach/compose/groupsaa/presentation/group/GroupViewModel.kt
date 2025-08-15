package com.alexyach.compose.groupsaa.presentation.group

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexyach.compose.groupsaa.domain.model.Group
import androidx.core.net.toUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupViewModel(
    group: Group
) : ViewModel() {

    private val _errorState: MutableStateFlow<GroupScreenErrorState> = MutableStateFlow(GroupScreenErrorState.NoError)
    val errorState: StateFlow<GroupScreenErrorState> = _errorState

    fun setGroupScreenErrorState(state: GroupScreenErrorState) {
        _errorState.value = state
    }


    /* *********** Action ************** */
    fun handlerGroupScreenAction(action: GroupScreenAction) {
        when(action) {
            is GroupScreenAction.CallGroup -> makeCallGroup(action.context, action.phoneNumber)
            is GroupScreenAction.GroupMap -> showGroupMap(action.context, action.addressForMap)

            is GroupScreenAction.LaunchSkype -> launchSkype(
                context = action.context,
                uri = action.uri,
                skypeLauncher = action.skypeLauncher,
                playStoreLauncher = action.playStoreLauncher
            )

            is GroupScreenAction.LaunchTelegram -> launchTelegram(
                context = action.context,
                uri = action.uri,
                telegramLauncher = action.telegramLauncher,
                playStoreLauncher = action.playStoreLauncher
            )

            is GroupScreenAction.LaunchZoom -> launchZoom(
                context = action.context,
                uri = action.uri,
                zoomLauncher = action.zoomLauncher,
                playStoreLauncher = action.playStoreLauncher
            )
        }
    }


    private fun makeCallGroup(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
//        Log.d("Logs","makeCallGroup phoneNumber: $phoneNumber")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Log.d("Logs", "makeCall Error")
        }
    }

    private fun showGroupMap(context: Context, addressForMap: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$addressForMap")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        try {
            context.startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d("Logs", "Error addressForMap")
        }

    }



    /* ************** Launchers **************** */
    private fun launchTelegram(
        context: Context,
        uri: String,
        telegramLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
        if (isAppInstalled(context, "org.telegram.messenger")) {
            telegramLauncher.launch(intent)
        } else {
//            errorMessage = "Telegram не встановлено"
            Log.d("Logs", "Telegram не встановлено")

            try {
                val packageName = "org.telegram.messenger"
                playStoreLauncher.launch(
                    Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=$packageName".toUri()
                    )
                )
            } catch (e: Exception) {
                _errorState.value = GroupScreenErrorState.TelegramError

                Log.d("Logs", "launchSkype error: $e")
            }
        }
    }

    private fun launchSkype(
        context: Context,
        uri: String,
        skypeLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri()) // Замініть на реальний Skype ID
        if (isAppInstalled(context, "com.skype.raider")) {
            skypeLauncher.launch(intent)
        } else {
//            errorMessage = "Skype не встановлено"
            Log.d("Logs", "Skype не встановлено")


            try {
                val packageName = "com.skype.raider"
                playStoreLauncher.launch(
                    Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=$packageName".toUri()
                    )
                )
            } catch (e: Exception) {
                _errorState.value = GroupScreenErrorState.SkypeError

                Log.d("Logs", "launchSkype error: $e")
            }
        }
    }

    private fun launchZoom(
        context: Context,
        uri: String,
        zoomLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        playStoreLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri()) // Замініть на реальний Zoom Meeting ID
        if (isAppInstalled(context, "us.zoom.videomeetings")) {
            zoomLauncher.launch(intent)
        } else {
//            errorMessage = "Zoom не встановлено"
            Log.d("Logs", "Zoom не встановлено")

            try {
                val packageName = "us.zoom.videomeetings"
                playStoreLauncher.launch(
                    Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=$packageName".toUri()
                    )
                )
            } catch (e: Exception) {
                _errorState.value = GroupScreenErrorState.ZoomError

                Log.d("Logs", "launchSkype error: $e")
            }
        }
    }


    // Функція для перевірки, чи встановлено додаток
    private fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }



}


