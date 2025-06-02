package com.alexyach.compose.groupsaa.presentation.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.domain.entity.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    group : Group,
    onBackPress : () -> Unit
) {

    val viewModel: GroupViewModel = viewModel(
        factory = GroupViewModelFactory(group = group)
    )

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(text = "! ${group.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier.padding(paddingValue)
        )
    }

}