package com.alexyach.compose.groupsaa.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Prayer
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel
import com.alexyach.compose.groupsaa.ui.theme.spice_rice
import com.alexyach.compose.groupsaa.ui.theme.triodionr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayersContent(
    viewModel: HomeViewModel,
    isUkrVoice: Boolean,
    infoPrayersListener : () -> Unit
) {

    val prayerList by viewModel.prayersList.collectAsState(emptyList<Prayer>())//getPrayersList())

    var isShowPrayerSetting by remember { mutableStateOf(false) }


    /* * TITLE * */
    PrayersTitle(
        viewModel = viewModel,
        isShowPrayerSetting = isShowPrayerSetting,
        isShowPrayerSettingListener = { isShowPrayerSetting = !isShowPrayerSetting },
        infoPrayersListener = infoPrayersListener
    )


    /* ** Prayers ** */
    if (prayerList.isNotEmpty()) {

        prayerList.forEach { prayer ->

            var isHidePrayer by remember { mutableStateOf(true) }
            var isVisiblePrayer by remember { mutableStateOf(true) }
            isVisiblePrayer = prayer.isVisible

            if (isVisiblePrayer || isShowPrayerSetting) {

                PrayerCard(
                    isHide = isHidePrayer,
                    onClickHideListener = { isHidePrayer = !isHidePrayer },
                    resTextTitle = stringResource(prayer.title),
                    resTextStart = stringResource(prayer.textShort),
                    resTextFull = stringResource(prayer.textFull),
                    isShowPrayerSetting = isShowPrayerSetting,
                    isShowPrayer = isVisiblePrayer,
                    onClickShowMorningPrayer = {
                        isVisiblePrayer = !isVisiblePrayer
                        prayer.isVisible = !prayer.isVisible

                        viewModel.savePrefPrayerList(
                            prayersEnum = prayer.name,
                            value = prayer.isVisible
                        )
                    },
                    viewModel = viewModel,
                    isUkrVoice = isUkrVoice
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrayersTitle(
    viewModel: HomeViewModel,
    isShowPrayerSetting: Boolean,
    isShowPrayerSettingListener: () -> Unit,
    infoPrayersListener : () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
//                .background(Color.Green)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(start = 8.dp)
//                .weight(0.2f)
                .clickable {
                    infoPrayersListener()
                }
        )


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(4f)
                .padding(start = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.homescreen_prayer_title),
                style = MaterialTheme.typography.titleLarge,
                fontFamily = triodionr
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .clickable {
//                    isShowPrayerSetting = !isShowPrayerSetting
                    isShowPrayerSettingListener()
                    /* load preferences visibility Prayers  */
                    viewModel.loadPrefPrayerList()
                }
        ) {
            Text(
                text = "show",
                style = MaterialTheme.typography.labelMedium,
                fontStyle = FontStyle.Italic,
                color = if (isShowPrayerSetting) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Icon(
                painterResource(R.drawable.hand_eye),
                contentDescription = "setting",
                tint = if (isShowPrayerSetting) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
            )
        }

    }

}

@Composable
fun PrayerCard(
    isHide: Boolean,
    onClickHideListener: () -> Unit,
    resTextTitle: String,
    resTextStart: String,
    resTextFull: String,
    isShowPrayerSetting: Boolean,
    isShowPrayer: Boolean,
    onClickShowMorningPrayer: () -> Unit,
    viewModel: HomeViewModel,
    isUkrVoice: Boolean
) {
    var fontSizeText by remember { mutableIntStateOf(16) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            .clickable { onClickHideListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        if (isShowPrayerSetting) {
            Checkbox(
                checked = isShowPrayer,
                onCheckedChange = {
                    onClickShowMorningPrayer()
                },
                modifier = Modifier
//                    .background(Color.Green)
                    .padding(start = 8.dp, top = 8.dp)
                    .size(15.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {


            /* Text Size */
            if (!isHide) {
                /* *** Speech *** */
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp)
                ) {
                    PlayAudioUkrText(
                        text = resTextFull,
                        viewModel = viewModel,
                        isUkrVoice = isUkrVoice
                    )
                }

                Row(
                    modifier = Modifier.weight(0.7f)
                ) {}

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "plus",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 8.dp)
                            .clickable { fontSizeText++ }
                    )

                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "minus",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { fontSizeText-- }
                    )
                }

            } else { if (viewModel.isSpeaking()) viewModel.stopUkrainianText() }
        }

        if (isHide) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = resTextTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spice_rice,
                )
                Text(
                    text = resTextStart,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = spice_rice,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrow"
                )

            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(color = MaterialTheme.colorScheme.primary)
            ) {

                Text(
                    text = resTextFull,
                    fontSize = fontSizeText.sp,
//                    fontFamily = triodionr,
                    fontFamily = spice_rice,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

        }

    }
}


@Composable
fun InfoPrayersContent(
    infoPrayersListener : () -> Unit
){

    val iconSize = 18.sp

    val inlineContent = mapOf(
        "show" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painterResource(R.drawable.hand_eye),
                contentDescription = "show",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "speech" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.text_to_speech),
                contentDescription = "speech",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "plus" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "plus",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },


        "minus" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.minus),
                contentDescription = "minus",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },

        "check" to InlineTextContent(
            Placeholder(
                width = iconSize,
                height = iconSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckBox,
                contentDescription = "check",
                tint = MaterialTheme.colorScheme.tertiary
            )
        },
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 56.dp, vertical = 120.dp)
            .fillMaxWidth()
//            .fillMaxHeight()
            .background(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
//                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(4.dp)
                .clip(
                    RoundedCornerShape(24.dp)
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
//                    .padding(start = 16.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.homescreen_prayers_info_1) + "  ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                        append("show")
                    }
                    appendInlineContent("show", "  show")
                    append("  " + stringResource(R.string.homescreen_prayers_info_2) + "  ")
                    appendInlineContent("check", "check")
                    append("  " + stringResource(R.string.homescreen_prayers_info_3))
                    append(stringResource(R.string.homescreen_prayers_info_4) + "  ")
                    appendInlineContent("plus", "plus")
                    append(" , ")
                    appendInlineContent("minus", "minus")
                    append("  " + stringResource(R.string.homescreen_prayers_info_5) + "  ")
                    appendInlineContent("speech", "speech")
                    append("  " + stringResource(R.string.homescreen_prayers_info_6))
                },
                inlineContent = inlineContent,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { infoPrayersListener() }
            )
        }
    }


}