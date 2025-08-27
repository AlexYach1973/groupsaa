package com.alexyach.compose.groupsaa.presentation.home.components

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.alexyach.compose.groupsaa.domain.usecase.CheckUpdateUseCase
import com.alexyach.compose.groupsaa.presentation.home.UpdateStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class UpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val checkUpdateUseCase: CheckUpdateUseCase,
) {
    private val _status = MutableStateFlow(UpdateStatus.CHECK_UPDATE)
    val status: StateFlow<UpdateStatus> = _status

    private var downloadId: Long? = null

    val versionName = context.packageManager.
    getPackageInfo(context.packageName, 0).versionName

    fun checkAndUpdate(owner: String, repo: String) {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val asset = checkUpdateUseCase(owner, repo)
                val latestVersion = extractVersionFromTag(asset?.name ?: "")

                if (latestVersion != null && latestVersion != versionName) {
                    _status.value = UpdateStatus.UPDATE_AVAILABLE

                    asset?.downloadUrl?.let { url ->
                        downloadApk(url)
                        registerReceiver()
                    }
                } else {
                    _status.value = UpdateStatus.NO_UPDATE
                }
            } catch (e: Exception) {
                _status.value = UpdateStatus.ERROR
                Log.d("Logs", "UpdateManager e: $e")
            }

        }
    }

    private fun extractVersionFromTag(name: String): String? {
        return Regex("v\\d+\\.\\d+\\.\\d+").find(name)?.value
    }

    private fun downloadApk(url: String) {
        val request = DownloadManager.Request(url.toUri()).apply {
            setTitle("Обновление приложения")
            setDescription("Загрузка новой версии")
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = manager.enqueue(request)
    }


    private fun registerReceiver() {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val receivedId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (receivedId == downloadId) {
                    installApk()
                }
            }
        }

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }


    private fun installApk() {
        val apkFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "update.apk"
        )
        val apkUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            apkFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(apkUri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }


}