package com.alexyach.compose.groupsaa.domain.model

enum class Prayers(var isVisible: Boolean) {
    MorningPrayer(true),
    EveningPrayer(true),
    DelegationPrayer(true),
    PeaceOfMindPrayer(true),
    ResentmentPrayer(true),
    FearPrayer(true),
    StepTenPrayer(true)
}

fun getAllPrayers() = enumValues<Prayers>().toList()