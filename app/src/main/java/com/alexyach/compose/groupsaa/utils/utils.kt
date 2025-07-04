package com.alexyach.compose.groupsaa.utils

import android.text.style.StyleSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.HtmlCompat
import com.alexyach.compose.groupsaa.domain.model.Group
import java.time.Period

/* Format Text Home Screen */
fun formatPeriod(period: Period): String {
    val years = period.years
    val months = period.months
    val days = period.days

    val yearsText = when {
        years == 0 -> ""
        years == 1 -> "$years рік"
        years in 2..4 -> "$years роки"
        else -> "$years років"
    }

    val monthsText = when {
        months == 0 -> ""
        months == 1 -> "$months місяць"
        months in 2..4 -> "$months місяці"
        else -> "$months місяців"
    }

    val daysText = when {
        days == 0 -> ""
        days == 1 -> "$days день"
        days in 2..4 -> "$days дні"
        else -> "$days днів"
    }

    // Об'єднання частин, виключаючи порожні
    return listOf(yearsText, monthsText, daysText)
        .filter { it.isNotEmpty() }
        .joinToString(" ")
}

fun formatTotalDays(days: Int): String {
    return when {
        days == 0 -> ""
        days == 1 -> "$days день"
        days in 2..4 -> "$days дні"
        else -> "$days днів"
    }
}

/* Утиліта для конвертації HTML у AnnotatedString */
@Composable
fun htmlToAnnotatedString(html: String): AnnotatedString {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    return buildAnnotatedString {
        append(spanned.toString())
        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            when (span) {
                is StyleSpan -> {
                    val start = spanned.getSpanStart(span)
                    val end = spanned.getSpanEnd(span)
                    when (span.style) {
                        android.graphics.Typeface.BOLD -> {
                            addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                        }
                        android.graphics.Typeface.ITALIC -> {
                            addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                        }
                    }
                }
            }
        }
    }
}


/* ************************* TEST ************************ */
fun getListGroupTest(): List<Group> {
    return listOf(
        Group(
            name = "\"Оболонь\"",
            addresses = " м.\"Героїв Дніпра\", вул. Прирічна, 21а  ",
            schedule = "щодня о 19:00",
            telephone = "097-476-3601",
            email = "https://www.aa.kiev.ua/grupa-aa-obolon/",
            note = "Підвальне приміщення з тилу будинку",
            latitude = 50.52046,
            longitude = 30.52119,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Фенікс\"",
            addresses = "м.\"Кловська\", Печерський узвіз, 19",
            schedule = "щодня о 19:00",
            telephone = "098-300-0101",
            email = "https://www.aa.kiev.ua/grupa-aa-feniks/",
            note = "",
            latitude = 50.43650,
            longitude = 30.53890,
            addressForMap = "Печерский+спуск,+19,+Киев"
        ),
        Group(
            name = "\"Дом на Горі\"",
            addresses = "м.\"Деміївська\", вул. Карпатської Січі, 2а",
            schedule = "щодня о 19:00",
            telephone = "095-774-3089",
            email = "https://www.aa.kiev.ua/dim-na-gori/",
            note = "Зібрання проводиться в Монастирі Сестер місіонерок любові",
            latitude = 50.40674,
            longitude = 30.50053,
            addressForMap = "ул.+Гвардейская,+2А,+Киев"
        ),
        Group(
            name = "\"Друг\"",
            addresses = "м.\"Шулявська\", вул. Довженка, 2 Соцслужба",
            schedule = "вівторок о 19:00",
            telephone = "044-384-2209",
            email = "https://www.aa.kiev.ua/grupa-aa-drug/",
            note = "Приміщення Київського Міського Центру Соціальних Служб для сім’ї, дітей та молоді." +
                    "У перший вівторок кожного місяця – спікерськє зібрання",
            latitude = 50.45648,
            longitude = 30.44696,
            addressForMap = "ул.+Александра+Довженко,+2,+Киев"
        ),
        Group(
            name = "\"Десна\"",
            addresses = "ж/м \"Троєщина\", вул. М.Закревського, 29а",
            schedule = "щодня о 19:00",
            telephone = "068-372-6878",
            email = "https://www.aa.kiev.ua/grupa-aa-desna/",
            note = "ЖЕД 305, другий поверх, направо, останні двері.",
            latitude = 50.50135,
            longitude = 30.60912,
            addressForMap = "ул.+Николая+Закревского,+29a,+Киев"
        ),
        Group(
            name = "\"Group\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Group 1\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Group 2\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Group\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Group\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"Group\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        ),
        Group(
            name = "\"END\"",
            addresses = " \"Centr\", вул. Srtreet, Yа",
            schedule = "щодня о 19:00",
            telephone = "XXX-XXX-XXXX",
            email = "https://www.aa.__________________",
            note = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.",
            latitude = 0.0,
            longitude = 0.0,
            addressForMap = "ул.+Приречная,+21А,+Киев"
        )

    )
}