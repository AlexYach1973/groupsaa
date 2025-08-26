package com.alexyach.compose.groupsaa.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel

@Composable
fun PlayAudioUkrText(
    text: String,
    viewModel: HomeViewModel,
    isUkrVoice: Boolean
) {

    if (isUkrVoice) {

        var isSpeaking by remember { mutableStateOf(true) }

        Icon(
            painterResource(R.drawable.text_to_speech),
            contentDescription = "Play",
            tint = if (isSpeaking) {
                MaterialTheme.colorScheme.tertiary } else {
                MaterialTheme.colorScheme.error },
//            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    if (viewModel.isSpeaking()) {
                        viewModel.stopUkrainianText()
                        isSpeaking = true
                    } else {
                        viewModel.speechUkrainianText(text)
                        isSpeaking = false
                    }

                }
        )
    }
}