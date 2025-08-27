package com.alexyach.compose.groupsaa.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel
import com.alexyach.compose.groupsaa.presentation.home.UpdateStatus


@Composable
fun UpdateScreen(
    viewModel: HomeViewModel,
) {

    val status by viewModel.updateStatus.collectAsState()

    var textStatus by remember { mutableStateOf("") }

    textStatus = when(status) {
        UpdateStatus.CHECK_UPDATE -> "Перевірити оновлення"
        UpdateStatus.NO_UPDATE -> "Остання версія"
        UpdateStatus.UPDATE_AVAILABLE -> "Є нова верся!"
        UpdateStatus.DOWNLOADING -> "Завантаження оновлення..."
        UpdateStatus.INSTALLED -> "Оновлення встановлено"
        UpdateStatus.ERROR -> "Помилка при перевірці оновлення"
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                viewModel.triggerUpdate()
            },
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(
                text = textStatus,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }



}