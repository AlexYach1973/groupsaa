package com.alexyach.compose.groupsaa.presentation.grouplist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderGroupList(
    isLoadFromInternet: Boolean,
    resStringTitle: Int,
    isOffline: () -> Unit,
    infoOfflineGroupListener: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
//                    .padding(start = 8.dp)
//                .weight(0.2f)
                    .clickable {
                        isOffline()
                        infoOfflineGroupListener()
                    }
            )

            Text(
                text = stringResource(resStringTitle),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )



//            if (isOffline) {
                Icon(
                    painterResource(
                        if (isLoadFromInternet) R.drawable.web_icon
                        else R.drawable.database_icon
                    ),
                    tint = Color.DarkGray,
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 8.dp)
                )
//            }
        }

    }


}


@Composable
fun InfoOfflineGroup(
    isOffline: Boolean,
    infoOfflineGroupListener : () -> Unit
) {

    val iconSize = 18.sp

    val inlineContent = mapOf(
        "offline" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.group),
                contentDescription = "show",
                tint = Color.Green
            )
        },

        "online" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.seminar),
                contentDescription = "show",
                tint = Color.Green
            )
        }
    )

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
                text = buildAnnotatedString {
                    append(stringResource(R.string.grouplistscreen_info))
                    if (isOffline){
                        append(stringResource(R.string.grouplistscreen_info_offline_1) + "  ")
                        appendInlineContent("offline", "  offline")
                        append("  " + stringResource(R.string.grouplistscreen_info_offline_2))

                    } else {
                        append(stringResource(R.string.grouplistscreen_info_online_1) + "  ")
                        appendInlineContent("online", "  online")
                        append("  " + stringResource(R.string.grouplistscreen_info_online_2))
                    }

                },
                inlineContent = inlineContent,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { infoOfflineGroupListener () }
            )

        }
    }

}