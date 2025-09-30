import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel
import com.alexyach.compose.groupsaa.presentation.home.UpdateStatus
import kotlinx.coroutines.delay


@Composable
fun UpdateScreen(
    context: Context,
    viewModel: HomeViewModel,
    infoUpdateListener: () -> Unit
) {

    val status by viewModel.status.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val gitHubRelease = viewModel.gitHubRelease.collectAsState()

//    Log.d("Logs", "UpdateScreen status: ${status}")


    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName ?: "ver.?.?.?"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
//        horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(start = 16.dp)
//                .weight(0.2f)
                    .clickable {
                        infoUpdateListener()
                    }
            )


            Text(
                text = "ver. $versionName",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        viewModel.checkForUpdate()
                    }
            )
        }


        when (status) {
            UpdateStatus.NoUpdate -> {
                DisappearingText(R.string.update_screen_no_update)
            }
            UpdateStatus.UpdateAvailable -> {
                DisappearingText(R.string.update_screen_available_update)
            }
            UpdateStatus.Downloading -> {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {

                    Text(
                        text = "Завантаження: $progress%",
                        style = MaterialTheme.typography.bodySmall
                    )

                    LinearProgressIndicator(
                        modifier = Modifier.padding(vertical = 8.dp),
                        progress = { progress / 100f },
                        color = ProgressIndicatorDefaults.linearColor,
                        trackColor = ProgressIndicatorDefaults.linearTrackColor,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )

                    Text(
                        text = "${gitHubRelease.value?.name ?: ""} від ${gitHubRelease.value?.published}" ,
//                        text = "${ gitHubRelease.value?.name ?: "" } від $formatedDate",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (gitHubRelease.value?.body?.isNotBlank() ?: false) {
                        Text(
                            text = gitHubRelease.value?.body ?: "",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }


//                }

//                CircularProgressIndicator(progress = { progress / 100f })


            }

            UpdateStatus.Installed -> {
//                Text(stringResource(R.string.update_screen_installed_update))
                DisappearingText(R.string.update_screen_installed_update)
            }
            UpdateStatus.PermissionRequired -> {
//                Text(stringResource(R.string.update_screen_required_update))
                DisappearingText(R.string.update_screen_required_update)
            }
            UpdateStatus.Idle -> {}
        }



    }

}



@Composable
fun DisappearingText(
    textId: Int
) {
    var showText by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(2000)  // Задержка в 2 секунды
        showText = false
    }

    AnimatedVisibility(
        visible = showText,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun InfoUpdateContent(
    infoUpdateListener: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(horizontal = 56.dp, vertical = 120.dp)
            .fillMaxWidth()
//            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
//                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(4.dp)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
//                    .padding(start = 16.dp)
            )


            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.homescreen_download_info_1) + " ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append("ver. 1.*.*")
                    }
                    append(" " + stringResource(R.string.homescreen_download_info_2))
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { infoUpdateListener() }
            )
        }


    }



}