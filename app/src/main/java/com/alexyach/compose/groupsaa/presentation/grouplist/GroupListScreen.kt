package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.domain.entity.Group


@Composable
fun GroupListScreen(
    paddingValues: PaddingValues,
    onGroupClickListener : (Group) -> Unit
) {
    val viewModel: GroupListViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.initial)
    val currentState = screenState.value
    when(currentState) {
        is GroupListScreenState.Groups -> {
            Groups(
                groups = currentState.group,
//                viewModel = viewModel,
                paddingValues = paddingValues,
                onGroupClickListener = onGroupClickListener
            )
        }
        GroupListScreenState.initial -> TODO()
    }


}


@Composable
private fun Groups(
    groups: List<Group>,
//    viewModel: GroupListViewModel,
    onGroupClickListener : (Group) -> Unit,
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 0.dp,
            end = 0.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(
            items = groups,
            key = { it.name }
        ) {

            CardGroup(
                group = it,
                onGroupClickListener
            )

        }

    }

}

//@Preview
//@Composable
//private fun ViewGroupListScreen() {
//    GroupListScreen(
//        paddingValues = PaddingValues(0.dp),
//        onGroupClickListener = {}
//    )
//}