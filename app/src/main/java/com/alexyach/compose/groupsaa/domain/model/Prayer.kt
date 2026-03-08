package com.alexyach.compose.groupsaa.domain.model

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