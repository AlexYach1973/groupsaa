package com.alexyach.compose.groupsaa.presentation.home

import UpdateScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexyach.compose.groupsaa.presentation.home.components.DailyReflectionCard
import com.alexyach.compose.groupsaa.presentation.home.components.PeriodOfSobrietyCard
import com.alexyach.compose.groupsaa.presentation.home.components.PrayersContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onReadClickListener: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {


        val context = LocalContext.current
        val viewModel: HomeViewModel = hiltViewModel()

        /* Version Info */
//        val versionCode = context.packageManager.
//                getPackageInfo(context.packageName, 0).versionCode

//        val versionName = context.packageManager.
//        getPackageInfo(context.packageName, 0).versionName

//        Log.i("Logs", "versionCode: $versionCode; versionName: $versionName")
        /* END Version Info */


        /* TTS */
        val isUkrVoice by viewModel.isUkrVoice.collectAsState(false)

        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {


            /* Update */
            UpdateScreen(
                context = context,
                viewModel = viewModel
            )

            /* * Period of Sobriety * */
            PeriodOfSobrietyCard(
                context = context,
                viewModel = viewModel,
            )

            /* ** Daily reflection ** */
            DailyReflectionCard(
                viewModel = viewModel,
                isUkrVoice = isUkrVoice
            )


            /* ***  PRAYERS ***  */
            PrayersContent(
                viewModel = viewModel,
                isUkrVoice = isUkrVoice
            )


        }

    }
}

