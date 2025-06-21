package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.ui.theme.GroupsaaTheme

@Composable
fun CardGroup(
    group: Group,
    onGroupClickListener : (Group) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.onSecondary
            ),

            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondaryContainer
            ),
        onClick = { onGroupClickListener(group) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DistanceToGroup(
                group = group,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            InfoAboutGroup(
                group = group,
                onGroupClickListener
            )
        }
    }

}


@Composable
private fun DistanceToGroup(group: Group){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 4.dp)
            .width(100.dp)

    ) {
        Icon(
            painterResource(id = R.drawable.group),
            contentDescription = "groups",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(30.dp)
        )

        Text(
            text = String.format("%.1f", group.distance) + "км",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }

}

@Composable
private fun InfoAboutGroup(
    group: Group,
    onGroupClickListener : (Group) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            text = group.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = group.addresses,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupsaaTheme {
        CardGroup(
            Group("Name", "qweqwe", "qw@qw", "3434","","",0.1, 0.1),
            {}
        )
    }
}