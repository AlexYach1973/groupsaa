package com.alexyach.compose.groupsaa.utils

import com.alexyach.compose.groupsaa.domain.entity.Group
import com.alexyach.compose.groupsaa.presentation.navigation.Screen

fun getListGroup(): List<Group> {
    return listOf(
        Group(
            name = "Оболонь",
            addresses = " м.\"Героїв Дніпра\", вул. Прирічна, 21а  ",
            schedule = "щодня о 19:00",
            telephone = "097-476-3601",
            email = "https://www.aa.kiev.ua/grupa-aa-obolon/",
            note = "Підвальне приміщення з тилу будинку",
            longitude = 50.52046,
            latitude = 30.52119,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "Фенікс",
            addresses = "м.\"Кловська\", Печерський узвіз, 19",
            schedule = "щодня о 19:00",
            telephone = "098-300-0101",
            email = "https://www.aa.kiev.ua/grupa-aa-feniks/",
            note = "",
            longitude = 50.43650,
            latitude = 30.53890,
            addressForMap = "Печерский+спуск,+19,+Киев"
        ),
        Group(
            name = "Дом на Горі",
            addresses = "м.\"Деміївська\", вул. Карпатської Січі, 2а",
            schedule = "щодня о 19:00",
            telephone = "095-774-3089",
            email = "https://www.aa.kiev.ua/dim-na-gori/",
            note = "Зібрання проводиться в Монастирі Сестер місіонерок любові",
            longitude = 50.40674,
            latitude = 30.50053,
            addressForMap = "ул.+Гвардейская,+2А,+Киев"
        ),
        Group(
            name = "Друг",
            addresses = "м.\"Шулявська\", вул. Довженка, 2 Соцслужба",
            schedule = "вівторок о 19:00",
            telephone = "044-384-2209",
            email = "https://www.aa.kiev.ua/grupa-aa-drug/",
            note = "Приміщення Київського Міського Центру Соціальних Служб для сім’ї, дітей та молоді." +
                    "У перший вівторок кожного місяця – спікерськє зібрання",
            longitude = 50.45648,
            latitude = 30.44696,
            addressForMap = "ул.+Александра+Довженко,+2,+Киев"
        ),
        Group(
            name = "Десна",
            addresses = "ж/м \"Троєщина\", вул. М.Закревського, 29а",
            schedule = "щодня о 19:00",
            telephone = "068-372-6878",
            email = "https://www.aa.kiev.ua/grupa-aa-desna/",
            note = "ЖЕД 305, другий поверх, направо, останні двері.",
            longitude = 50.50135,
            latitude = 30.60912,
            addressForMap = "ул.+Николая+Закревского,+29a,+Киев"
        ),

        )
}