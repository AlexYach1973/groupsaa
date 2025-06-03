package com.alexyach.compose.groupsaa.utils

import com.alexyach.compose.groupsaa.domain.entity.Group
import com.alexyach.compose.groupsaa.presentation.navigation.Screen

fun getListGroup(): List<Group> {
    return listOf(
        Group(
            name = "Оболонь",
            addresses = " \"Героїв Дніпра\", вул. Прирічна, 21а  ",
            schedule = "щодня о 19:00",
            telephone = "097-476-3601",
            email = "https://www.aa.kiev.ua/grupa-aa-obolon/",
            note = "Підвальне приміщення з тилу будинку"
        ),
        Group(
            name = "Фенікс",
            addresses = "м.\"Кловська\", Печерський узвіз, 19",
            schedule = "щодня о 19:00",
            telephone = "098-300-0101",
            email = "https://www.aa.kiev.ua/grupa-aa-feniks/",
            note = ""
        ),
        Group(
            name = "Дом на Горі",
            addresses = "м.\"Деміївська\", вул. Карпатської Січі, 2а",
            schedule = "щодня о 19:00",
            telephone = "095-774-3089",
            email = "https://www.aa.kiev.ua/dim-na-gori/",
            note = "Зібрання проводиться в Монастирі Сестер місіонерок любові"
        ),
        Group(
            name = "Друг",
            addresses = "м.\"Шулявська\", вул. Довженка, 2 Соцслужба",
            schedule = "вівторок о 19:00",
            telephone = "044-384-2209",
            email = "https://www.aa.kiev.ua/grupa-aa-drug/",
            note = "Приміщення Київського Міського Центру Соціальних Служб для сім’ї, дітей та молоді." +
                    "У перший вівторок кожного місяця – спікерськє зібрання"
        ),
        Group(
            name = "Десна",
            addresses = "ж/м \"Троєщина\", вул. М.Закревського, 29а",
            schedule = "щодня о 19:00",
            telephone = "068-372-6878",
            email = "https://www.aa.kiev.ua/grupa-aa-desna/",
            note = "ЖЕД 305, другий поверх, направо, останні двері."
        ),

        )
}