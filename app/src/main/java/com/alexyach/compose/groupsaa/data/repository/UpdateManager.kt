package com.alexyach.compose.groupsaa.data.repository

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.core.net.toUri
import com.alexyach.compose.groupsaa.domain.usecase.CheckLatestReleaseUseCase
import com.alexyach.compose.groupsaa.presentation.home.UpdateStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val useCase: CheckLatestReleaseUseCase,
) {

    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress

    private val _status = MutableStateFlow<UpdateStatus>(UpdateStatus.Idle)
    val status: StateFlow<UpdateStatus> = _status


    val currentVersion =  context.packageManager.
    getPackageInfo(context.packageName, 0).versionName // "1.0.1"


    fun checkAndUpdate(owner: String, repo: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val release = useCase.invoke(owner, repo)
            val asset = release?.assets?.find { it.name.endsWith(".apk") }

            val latestVersion = release?.tagName?.substringAfter("v")

            Log.i("Logs", "UpdateManager currentVersion= $currentVersion; latestVersion= $latestVersion")

            if (latestVersion != null && latestVersion != currentVersion) {
                _status.value = UpdateStatus.UpdateAvailable
                Log.i("Logs", "_status.value = UpdateStatus.UpdateAvailable")
                asset?.downloadUrl?.let { downloadApk(it) }
            } else {
                _status.value = UpdateStatus.NoUpdate
                Log.i("Logs", "_status.value = UpdateStatus.NoUpdate")
            }
        }

    }

    private fun downloadApk(url: String) {
        val request = DownloadManager.Request(url.toUri()).apply {
            setTitle("Обновление")
            setDescription("Загрузка новой версии")
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }

        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = manager.enqueue(request)
        trackProgress(manager, downloadId)
    }

    private fun trackProgress(manager: DownloadManager, downloadId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val query = DownloadManager.Query().setFilterById(downloadId)
            var downloading = true

            while (downloading) {
                val cursor = manager.query(query)
                if (cursor != null && cursor.moveToFirst()) {
                    val total = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val downloaded = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        val uri = manager.getUriForDownloadedFile(downloadId)
                        cursor.close()
                        installApk(uri)
                        downloading = false
//                        _status.value = UpdateStatus.Installed
//                        Log.i("Logs", "_status.value = UpdateStatus.Installed")
                        return@launch
                    }

                    if (total > 0) {
                        val percent = (downloaded * 100) / total
                        _progress.value = percent
                        _status.value = UpdateStatus.Downloading
//                        Log.i("Logs", "_status.value = UpdateStatus.Downloading")
                    }
                }
                cursor?.close()
                delay(500)
            }
        }
    }

    private fun installApk(uri: Uri) {
        if ( !context.packageManager.canRequestPackageInstalls() ) {
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                data = "package:${context.packageName}".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            _status.value = UpdateStatus.PermissionRequired
            Log.i("Logs", "_status.value = UpdateStatus.PermissionRequired")
            return
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)

        _status.value = UpdateStatus.Idle

    }



}