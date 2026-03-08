package com.alexyach.compose.groupsaa.domain.model

import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R

data class Book(
    val title: String,
    val description: String = "",
    val image: Int = R.drawable.book2,
    val uri: String = "",
)


fun getBook(
    uriHandler: UriHandler
) = listOf(
    Book(
        title = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)) {
                append("AA")
            }
        }.text,
        image = R.drawable.book_bigaa,
        uri = "https://www.aa.kiev.ua/knyga-anonimni-alkogoliky/"
    ),

    Book(
        title = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)) {
                append("12 ")
                append("\u00D7")
                append(" 12")
            }
        }.text,
        image = R.drawable.book_12_12,
        uri = "https://www.aa.kiev.ua/dvanadcyat-krokiv-anonimnix-alkogolikiv/"
    ),
    Book(
        title = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                append("Жити тверезо")
            }
        }.text,
        image = R.drawable.book_live_soberly,
        uri = "https://www.aa.kiev.ua/zhyty-tverezo/"
    )
    ,
    Book(title = "Test"),
    Book(title = "Test")
)
