package com.alexyach.compose.groupsaa.domain.model

import androidx.compose.ui.text.AnnotatedString
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.utils.peaceOfMindText
import com.alexyach.compose.groupsaa.utils.prayerDelegationText
import com.alexyach.compose.groupsaa.utils.prayerElevenEveningText
import com.alexyach.compose.groupsaa.utils.prayerElevenMorningText
import com.alexyach.compose.groupsaa.utils.prayerOfFearText
import com.alexyach.compose.groupsaa.utils.prayerPowerlessnessText
import com.alexyach.compose.groupsaa.utils.prayerTenText
import com.alexyach.compose.groupsaa.utils.resentmentText
import com.alexyach.compose.groupsaa.utils.stepOneAndTwoText
import com.alexyach.compose.groupsaa.utils.stepSevenText
import com.alexyach.compose.groupsaa.utils.stepSixText

data class Prayer(
    val name: PrayersEnum,
    var isHide: Boolean = false,
    var isVisible: Boolean = true,
)

fun getPrayersList(): List<Prayer> {
    return listOf(
        Prayer(name = PrayersEnum.MorningPrayer),
        Prayer(name = PrayersEnum.EveningPrayer),
        Prayer(name = PrayersEnum.DelegationPrayer),
        Prayer(name = PrayersEnum.PeaceOfMindPrayer),
        Prayer(name = PrayersEnum.ResentmentPrayer),
        Prayer(name = PrayersEnum.FearPrayer),
        Prayer(name = PrayersEnum.StepTenPrayer),
        Prayer(name = PrayersEnum.StepOnePrayer),
        Prayer(name = PrayersEnum.StepSixPrayer),
        Prayer(name = PrayersEnum.StepSevenPrayer),
        Prayer(name = PrayersEnum.PrayerForPowerlessness),
    )
}