package com.alexyach.compose.groupsaa.presentation.news

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.alexyach.compose.groupsaa.utils.peaceOfMindText
import com.alexyach.compose.groupsaa.utils.*
import com.alexyach.compose.groupsaa.utils.prayerTenText
import com.alexyach.compose.groupsaa.utils.resentmentText


@Composable
fun NewsScreen(
    paddingValues: PaddingValues
) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.secondaryContainer)
//    ) {

        val scrollState = rememberScrollState()

        var isHide3 by remember { mutableStateOf(true) }
        var isHide11Morning by remember { mutableStateOf(true) }
        var isHide11Evening by remember { mutableStateOf(true) }
        var isHidePeaceOfMind by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .background(Color.DarkGray)
        ) {



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    /**/
                    .animateContentSize()
                    .clickable { isHide3 = !isHide3 }
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {

                if (isHide3) {
                    Text(
                        text = prayerDelegationText(24, 14),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Text(
                        text = prayerDelegationText(18, 18),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }

            /* 11 Morning */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    /**/
                    .animateContentSize()
                    .clickable { isHide11Morning = !isHide11Morning }
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {

                if (isHide11Morning) {
                    Text(
                        text = prayerElevenMorningText(26, 18),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Text(
                        text = prayerElevenMorningText(22, 20),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }


            /* 11 Evening */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    /**/
                    .animateContentSize()
                    .clickable { isHide11Evening= !isHide11Evening }
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {

                if (isHide11Evening) {
                    Text(
                        text = prayerElevenEveningText(26, 18),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Text(
                        text = prayerElevenEveningText(22, 20),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            /* peace of mind */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    /**/
                    .animateContentSize()
                    .clickable { isHidePeaceOfMind= !isHidePeaceOfMind }
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {

                if (isHidePeaceOfMind) {
                    Text(
                        text = prayerPowerlessnessText(26, 18),
//                        text = stepSevenText(26, 18),
//                        text = stepSixText(26, 18),
//                        text = stepOneAndTwoText(26, 18),
//                        text = prayerTenText(26, 18),
//                        text = prayerOfFearText(26, 18),
//                        text = resentmentText(26, 18),
//                        text = peaceOfMindText(26, 18),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                    )
                } else {
                    Text(
                        text = prayerPowerlessnessText(22, 20),
//                        text = stepSevenText(22, 20),
//                        text = stepSixText(22, 20),
//                        text = stepOneAndTwoText(22, 20),
//                        text = prayerTenText(22, 20),
//                        text = prayerOfFearText(22, 20),
//                        text = resentmentText(22, 20),
//                        text = peaceOfMindText(22, 20),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }





        }
    }

//}