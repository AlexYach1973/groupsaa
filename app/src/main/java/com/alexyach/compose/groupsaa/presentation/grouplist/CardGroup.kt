package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.entity.Group
import com.alexyach.compose.groupsaa.ui.theme.GroupsaaTheme

@Composable
fun CardGroup(
    group: Group,
    onGroupClickListener : (Group) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondaryContainer
            )
    //        .clickable{
//            onGroupClickListener(group)
//        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
//            .size(50.dp),

    ) {
        Text(
            text = String.format("%.1f", group.distance) + "км",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
//        text = group.distance.toString()
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
            .clickable{ onGroupClickListener(group) }
    ) {
        Text(
            text = group.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = group.addresses,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
//            fontSize = 18.sp,
//            fontFamily = FontFamily.Cursive
        )

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupsaaTheme {
//        CardGroup()
    }
}