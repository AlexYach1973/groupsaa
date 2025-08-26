package com.alexyach.compose.groupsaa.presentation.group.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.group.GroupViewModel

@Composable
fun LaunchOnlineConference(
    viewModel: GroupViewModel,
    resIcon: Int,
    onClickLaunchListener: (GroupViewModel) -> Unit
) {
//    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickLaunchListener(viewModel)
            }

            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
            )
    ) {

        Image(
            painter = painterResource(id = resIcon),
            contentDescription = "icon",
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .weight(0.3f)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
        ) {

            Text(
                text = stringResource(R.string.groupscreen_online_chat),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
