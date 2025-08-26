package com.alexyach.compose.groupsaa.presentation.grouplist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.grouplist.FilterGroupsState
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListViewModel

@Composable
fun TopAppBarContent(
    viewModel: GroupListViewModel,
    filterForGroups: FilterGroupsState,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//                    .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(top = 48.dp)
    ) {
        Button(
            onClick = { viewModel.setFilterForGroups(FilterGroupsState.All) },

            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
//                        containerColor = Color.Transparent,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 0.dp)
//                        .size(height = 38.dp, width = 110.dp)
                .border(
                    width = 1.dp,
                    color = if (filterForGroups == FilterGroupsState.All) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    },

                    shape = RoundedCornerShape(16.dp)
                )

        ) {
            Text(
                text = stringResource(R.string.grouplistscreen_button_all),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }

        Button(
            onClick = { viewModel.setFilterForGroups(FilterGroupsState.Today) },
            colors = ButtonColors(

                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 0.dp)
//                                .size(height = 38.dp, width = 110.dp)
                .border(
                    width = 1.dp,
                    color = if (filterForGroups == FilterGroupsState.Today) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    },
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.grouplistscreen_button_today),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }

        Button(
            onClick = { viewModel.setFilterForGroups(FilterGroupsState.Now) },
            colors = ButtonColors(

                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.DarkGray
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 0.dp)
//                                .size(height = 38.dp, width = 110.dp)
                .border(
                    width = 1.dp,
                    color = if (filterForGroups == FilterGroupsState.Now) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    },
                    shape = RoundedCornerShape(16.dp)
                )

        ) {
            Text(
                text = stringResource(R.string.grouplistscreen_button_now),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
    }
}