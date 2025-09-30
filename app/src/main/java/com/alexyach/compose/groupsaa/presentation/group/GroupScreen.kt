package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.components.GroupScreenOffline
import com.alexyach.compose.groupsaa.presentation.group.components.GroupScreenOnline
import com.alexyach.compose.groupsaa.presentation.group.components.InfoGroupOffline
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    group: Group,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: GroupViewModel = viewModel(
        factory = GroupViewModelFactory(group = group)
    )

    var isShowInfoGroup by remember { mutableStateOf(false) }

    /* Errors State observable */
    val errorState = viewModel.errorState.collectAsState(GroupScreenErrorState.NoError)

    LaunchedEffect(errorState.value) {
        when(errorState.value) {
            GroupScreenErrorState.NoError -> {}
            is GroupScreenErrorState.TelegramError -> { toastShow(context, "Launch Telegram error") }
            GroupScreenErrorState.SkypeError -> { toastShow(context, "Launch Skype error") }
            GroupScreenErrorState.ZoomError -> { toastShow(context, "Launch Zoom error") }
        }
        viewModel.setGroupScreenErrorState(GroupScreenErrorState.NoError)
    }
    /* END Errors observable */


    /* *** Tooltip *** */
//    val tooltipState = rememberTooltipState(isPersistent = true)
//    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 50.dp)

                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer),
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            painterResource(R.drawable.left_arrow_button),
//                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                },

                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(end = 8.dp)
//                .weight(0.2f)
                            .clickable {
                                isShowInfoGroup = !isShowInfoGroup
                            }
                    )
                }
            )
        },

        ) { paddingValue ->
        val pv = paddingValue

        if (
        /* group.addressForMap.contains("t.me") ||
         group.addressForMap.contains("skype") ||
         group.addressForMap.contains("zoom")*/
            group.addressForMap.contains("http")
        ) {
            GroupScreenOnline(
                context = context,
                group = group,
                viewModel = viewModel
            )

        } else {
            GroupScreenOffline(
                context = context,
                group = group,
                viewModel = viewModel,
                showInfoGroupListener = { isShowInfoGroup = !isShowInfoGroup }
            )
        }


        if (isShowInfoGroup) {
            InfoGroupOffline(
                group = group,
                showInfoGroupListener = { isShowInfoGroup = !isShowInfoGroup }
            )
        }


    }

}


/* Toast */
private fun toastShow(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}





@Preview
@Composable
private fun ViewGroupScreen() {
    GroupScreen(
        Group(
            "Obolon",
            "qwefwe werwerwer werqwerqe qwer 12",
            listOf("19:00", "19:00", "19:00", "19:00", "19:00,10:00", "19:00", "19:00"),
            "qw@wqBlaBlaBlaBlaBlaBla",
            "123-3221",
            "BlaBlaBlaBlaBlaBla BlaBlaBla BlaBlaBlaBlaBlaBlaBlaBlaBla BlaBlaBla",
            1.1, 2.2
        ),
        { }
    )
}