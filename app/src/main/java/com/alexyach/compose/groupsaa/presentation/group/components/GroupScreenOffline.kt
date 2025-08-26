package com.alexyach.compose.groupsaa.presentation.group.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreenAction
import com.alexyach.compose.groupsaa.presentation.group.GroupViewModel

@Composable
fun GroupScreenOffline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel
) {
    val scrollState = rememberScrollState()
//    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                top = 124.dp,
                end = 8.dp,
                start = 8.dp,
                bottom = 132.dp
            )
            .fillMaxSize()
            .verticalScroll(scrollState),

        ) {

        AddressGroup(
            group = group,
            onClickLaunchMapListener = {
                viewModel.handlerGroupScreenAction(
                    GroupScreenAction.GroupMap(
                        context = context,
                        addressForMap = group.addressForMap
                    )
                )
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))

        TelephoneGroup(
            context = context,
            viewModel = viewModel,
            group = group
        )
        Spacer(Modifier.padding(8.dp))

        InternetLink(group = group)

        Spacer(modifier = Modifier.padding(8.dp))

        GroupSchedule(group = group)

        Spacer(modifier = Modifier.padding(8.dp))

        if (group.note.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
//                        brush = brush,
//                        shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
                    )
            ) {
                Text(
                    text = group.note,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
//                fontSize = 22.sp,
                )
            }
        }
    }
}
