package com.alexyach.compose.groupsaa.presentation.home

import InfoUpdateContent
import UpdateScreen
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexyach.compose.groupsaa.presentation.home.components.DailyReflectionCard
import com.alexyach.compose.groupsaa.presentation.home.components.InfoDailyReflectionContent
import com.alexyach.compose.groupsaa.presentation.home.components.InfoPrayersContent
import com.alexyach.compose.groupsaa.presentation.home.components.PeriodOfSobrietyCard
import com.alexyach.compose.groupsaa.presentation.home.components.PrayersContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onReadClickListener: () -> Unit
) {

    var isShowInfoUpdate by remember { mutableStateOf(false) }
    var isShowInfoDailyReflection by remember { mutableStateOf(false) }
    var isShowInfoPrayers by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {


        val context = LocalContext.current
        val viewModel: HomeViewModel = hiltViewModel()

        /* Collapsing Period of Sobriety Card */
        val lazyColumnState = rememberLazyListState()

        val collapseFraction by remember {
            derivedStateOf {
                val first = lazyColumnState.firstVisibleItemIndex
                val offset = lazyColumnState.firstVisibleItemScrollOffset
                (first * 200f + offset) / 200f
                    .coerceIn(0f, 1f)
            }
        }
//        Log.d("Logs", "collapseFraction= $collapseFraction") // 0.0 - 3129.0
        /* END Collapsing */

        /* TTS */
        val isUkrVoice by viewModel.isUkrVoice.collectAsState(false)

//        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
//                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {


            /* Update */
            UpdateScreen(
                context = context,
                viewModel = viewModel,
                infoUpdateListener = { isShowInfoUpdate = !isShowInfoUpdate }
            )



            /* * Collapsing Card Period of Sobriety * */
            /** smooth scrolling calculation
            collapseFraction = 185
             * x * collapseFraction => 1
             * x = 0.005
             **/
            val coeffCollapseFraction = 0.005f
            val cardHeight = (180 - 116 * collapseFraction * coeffCollapseFraction)
                .coerceAtLeast(64f).dp

//            Log.d("Logs", "height= ${(200 - 120 * collapseFraction)}")
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
            ) {

                PeriodOfSobrietyCard(
                    context = context,
                    viewModel = viewModel,
                    collapseFraction = collapseFraction * coeffCollapseFraction,
                    cardHeight = cardHeight
                )
            }

            Spacer(Modifier.height(8.dp))
//            HorizontalDivider(thickness = 1.dp)

            LazyColumn(
                state = lazyColumnState
            ) {
                item {
                    /* ** Daily reflection ** */
                    DailyReflectionCard(
                        viewModel = viewModel,
                        isUkrVoice = isUkrVoice,
                        infoDailyListener = { isShowInfoDailyReflection = !isShowInfoDailyReflection }
                    )
                }

                item {
                    /* ***  PRAYERS ***  */
                    PrayersContent(
                        viewModel = viewModel,
                        isUkrVoice = isUkrVoice,
                        infoPrayersListener = { isShowInfoPrayers = !isShowInfoPrayers }
                    )
                }

            }

        }

    }

    if (isShowInfoUpdate) {
        InfoUpdateContent(
            infoUpdateListener = { isShowInfoUpdate = !isShowInfoUpdate }
        )
    }

    if (isShowInfoDailyReflection) {
        InfoDailyReflectionContent(
            infoDailyListener = { isShowInfoDailyReflection = !isShowInfoDailyReflection }
        )
    }

    if (isShowInfoPrayers) {
        InfoPrayersContent(
            infoPrayersListener = { isShowInfoPrayers = !isShowInfoPrayers }
        )
    }


}

