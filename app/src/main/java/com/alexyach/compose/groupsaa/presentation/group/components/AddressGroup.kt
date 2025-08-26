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
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group


@Composable
fun AddressGroup(
    group: Group,
    onClickLaunchMapListener: () -> Unit
) {
//    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickLaunchMapListener()
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
            )
    ) {

        Image(
            painterResource(R.drawable.google_maps_logo),
            contentDescription = "google map logo",
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.addresses,
                style = MaterialTheme.typography.bodyLarge,
//                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp)
            )
        }


    }
}

