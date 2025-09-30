package com.alexyach.compose.groupsaa.presentation.group.components

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreenAction
import com.alexyach.compose.groupsaa.presentation.group.GroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreenOffline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel,
    showInfoGroupListener : () -> Unit
//    tooltipState : TooltipState,
//    scope: CoroutineScope
) {
    val scrollState = rememberScrollState()


    ContentGroupScreenOffline(
        context = context,
        group = group,
        viewModel = viewModel,
        scrollState = scrollState
    )
}


@Composable
fun InfoGroupOffline(
    group: Group,
    showInfoGroupListener : () -> Unit
) {
//    Log.d("Logs", "TooltipContentGroupOffline")

    val iconSize = 18.sp

    val inlineContent = mapOf(
        "google_map" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.map1),
                contentDescription = "google_map",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "call" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                Icons.Default.Call,
                contentDescription = "call",
                tint = Color.Green
            )
        },

        "website" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.website),
                contentDescription = "website",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "clock" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.handwatch),
                contentDescription = "clock",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "website" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.website),
                contentDescription = "website",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },




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

                    append(stringResource(R.string.groupscreen_info_offline_1) + "  ")
                    append(group.name)
                    append("  " + stringResource(R.string.groupscreen_info_offline_2))

                    if (group.addressForMap.contains("http")) {
                        append(stringResource(R.string.groupscreen_info_online_maps_1))
                    } else {
                        append(stringResource(R.string.groupscreen_info_offline_maps_1) + "  ")
                        appendInlineContent("google_map", "google map")
                        append("  " + stringResource(R.string.groupscreen_info_offline_maps_2))
                    }

                    if (group.telephone.isNotEmpty()) {
                        append(stringResource(R.string.groupscreen_info_offline_call_1) + "  ")
                        appendInlineContent("call", "call")
                        append("  " + stringResource(R.string.groupscreen_info_offline_call_2))
                    }

                    if (group.email.isNotEmpty()) {
                        append(stringResource(R.string.groupscreen_info_offline_website_1) + "  ")
                        appendInlineContent("website", "website")
                        append("  " + stringResource( R.string.groupscreen_info_offline_website_2))
                    }


                },
                inlineContent = inlineContent,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        showInfoGroupListener()
                    }
            )

        }

    }




}


@Composable
private fun ContentGroupScreenOffline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel,
    scrollState: ScrollState
){
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

        if (group.email.isNotEmpty()) {
            InternetLink(group = group)
            Spacer(modifier = Modifier.padding(8.dp))
        }

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
