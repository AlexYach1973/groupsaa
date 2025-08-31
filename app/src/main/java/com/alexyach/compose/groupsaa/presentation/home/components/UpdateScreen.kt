import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel
import com.alexyach.compose.groupsaa.presentation.home.UpdateStatus
import kotlinx.coroutines.delay


@Composable
fun UpdateScreen(
    context: Context,
    viewModel: HomeViewModel,
) {

    val status by viewModel.status.collectAsState()
    val progress by viewModel.progress.collectAsState()

//    Log.d("Logs", "UpdateScreen status: ${status}")


    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName ?: "?"

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {


        Text(
            text = "ver. $versionName",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    viewModel.checkForUpdate()
                }
        )

        when (status) {
            UpdateStatus.NoUpdate -> {
                DisappearingText(R.string.update_screen_no_update)
            }
            UpdateStatus.UpdateAvailable -> {
                DisappearingText(R.string.update_screen_available_update)
            }
            UpdateStatus.Downloading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Text(
                        text = "Загрузка: $progress%",
                        style = MaterialTheme.typography.bodySmall
                    )

                    LinearProgressIndicator(
                        modifier = Modifier.padding(vertical = 8.dp),
                        progress = { progress / 100f },
                        color = ProgressIndicatorDefaults.linearColor,
                        trackColor = ProgressIndicatorDefaults.linearTrackColor,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )


                }

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