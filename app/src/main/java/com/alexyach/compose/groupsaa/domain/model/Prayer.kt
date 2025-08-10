package com.alexyach.compose.groupsaa.domain.model

import androidx.compose.ui.res.stringResource
import com.alexyach.compose.groupsaa.R

data class Prayer(
    val name: PrayersEnum,
    val title: Int,
    val textShort: Int,
    val textFull: Int,
    var isHide: Boolean = false,
    var isVisible: Boolean = true,
)

fun getPrayersList(): List<Prayer> {
    return listOf(
        Prayer(
            name = PrayersEnum.MorningPrayer,
            title = R.string.number_step_eleven_morning_title,
            textShort = R.string.number_step_eleven_morning_start,
            textFull = R.string.number_step_eleven_morning
        ),
        Prayer(
            name = PrayersEnum.EveningPrayer,
            title = R.string.number_step_eleven_evening_title,
            textShort = R.string.number_step_eleven_evening_start,
            textFull = R.string.number_step_eleven_evening
        ),

        Prayer(
            name = PrayersEnum.DelegationPrayer,
            title = R.string.prayer_delegation_title,
            textShort = R.string.prayer_delegation_start,
            textFull = R.string.prayer_delegation
        ),

        Prayer(
            name = PrayersEnum.PeaceOfMindPrayer,
            title = R.string.peace_of_mind_title,
            textShort = R.string.peace_of_mind_start,
            textFull = R.string.peace_of_mind_full
        ),

        Prayer(
            name = PrayersEnum.ResentmentPrayer,
            title = R.string.resentment_title,
            textShort = R.string.resentment_start,
            textFull = R.string.resentment_full
        ),

        Prayer(
            name = PrayersEnum.FearPrayer,
            title = R.string.prayer_fear_title,
            textShort = R.string.prayer_fear_start,
            textFull = R.string.prayer_fear_full
        ),
        Prayer(
            name = PrayersEnum.StepTenPrayer,
            title = R.string.step_number_ten_title,
            textShort = R.string.step_number_ten_start,
            textFull = R.string.step_number_ten_full
        )
    )
}