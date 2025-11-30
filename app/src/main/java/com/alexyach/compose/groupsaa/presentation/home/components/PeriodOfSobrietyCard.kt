package com.alexyach.compose.groupsaa.presentation.home.components

import HomeScreenSelDateState
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodOfSobrietyCard(
    context: Context,
    viewModel: HomeViewModel,
    collapseFraction : Float
) {
    val selDateState =
        viewModel.selDateScreenState.collectAsState(HomeScreenSelDateState.Initial).value

    /* Animate values for Collapsing */
    val cardHeight by animateDpAsState(
        targetValue = (180 - 116 * collapseFraction).coerceAtLeast(64f).dp,
        label = "cardHeight"
    )

    val cardPaddingVertical by animateDpAsState(
        targetValue = (8f - 7 * collapseFraction).coerceAtLeast(1f).dp,
        label = "cardPaddingVertical"
    )

    val titleFontSize by animateFloatAsState(
        targetValue = (26 - 6f * collapseFraction).coerceAtLeast(20f),
        label = "titleFontSize"
    )

    val opacity by animateFloatAsState(
        targetValue = 1f - collapseFraction,
        label = "opacity"
    )

    /* END Animate values for Collapsing */

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
//            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = cardPaddingVertical)
            .height(cardHeight)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            ),

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        var openDialog by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        if (openDialog) {
            DataSelection(
                datePickerState = datePickerState,
                onDismissClickListener = { openDialog = it },
                onConfirmClickListener =
                    {
                        viewModel.dataPickerSelected(datePickerState.selectedDateMillis)
//                        viewModel.loadDateFromDataStore()
                    }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            if (opacity > 0.01f) {
                Image(
                    painter = painterResource(id = R.drawable.shar),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds, // Масштабування, щоб заповнити екран
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
//                    .background(Color.Gray)

            ) {
                /* 1 Line */
                if (opacity > 0.01f) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = context.getString(R.string.homescreen_sobriety_card_title),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Normal
//                        fontSize = 28.sp
//                        fontStyle = FontStyle.Italic
                        )
                    }
                }

                when (selDateState) {
                    is HomeScreenSelDateState.SelectedDate -> {

                        PeriodOfSobriety(
                            selDateState = selDateState,
                            openDialogListener = { openDialog = true },
                            opacity = opacity,
                            fontSize = titleFontSize
                        )

                    }

                    HomeScreenSelDateState.Error -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = context.getString(R.string.homescreen_sobriety_not_date),
                                style = MaterialTheme.typography.bodyLarge,
                                fontStyle = FontStyle.Italic
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { openDialog = true }
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Edit",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "setting",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        }
                    }

                    HomeScreenSelDateState.Loading -> {
                        CircularProgressIndicator()
                    }

                    HomeScreenSelDateState.Initial -> {}
                }

            }

        } // Box
    }

}

@Composable
private fun PeriodOfSobriety(
    selDateState: HomeScreenSelDateState.SelectedDate,
    openDialogListener: () -> Unit,
    opacity: Float,
    fontSize: Float
) {
    /* 2 Line */
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .background(Color.Green)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = selDateState.selDate.difference,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp
//            fontSize = 26.sp
        )

    }


    if (opacity > 0.01f) {
        /* 3 Line */
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "або ${selDateState.selDate.totalDays}",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                fontSize = 22.sp
            )
        }

        /* 4 Line */
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
//            .background(Color.Green)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .weight(4f)
            ) {
                Text(
                    text = "від ${selDateState.selDate.selectedDateSobriety}",
                    style = MaterialTheme.typography.labelMedium
                )
            }


            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .clickable { openDialogListener() /*openDialog = true*/ }
            ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.labelMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "setting",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}
