package com.alexyach.compose.groupsaa.presentation.read

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Book
import com.alexyach.compose.groupsaa.domain.model.getBook


@Composable
fun ReadScreen(
    paddingValues: PaddingValues
) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold() {
        val pValue = it


        Box(
            modifier = Modifier
                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.primary)
//                .background(MaterialTheme.colorScheme.surfaceVariant)
//                .background(MaterialTheme.colorScheme.outlineVariant) //+
//                .background(MaterialTheme.colorScheme.inversePrimary) // +
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            val scrollStateBookList = rememberScrollState()
            val verticalScrollState = rememberScrollState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(verticalScrollState)
                    .fillMaxSize()
//                .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {

                Text(
                    text = stringResource(R.string.readscreen_iteratura),
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .horizontalScroll(scrollStateBookList)
                        .padding(8.dp)
                ) {
                    getBook(uriHandler).forEach {book ->

                        BookCard(
                            title = book.title,
                            imageIdRes = book.image,
                            description = book.description,
                            onClickBookListener = {
                                try {
                                    uriHandler.openUri(book.uri)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Не вдалося", Toast.LENGTH_LONG).show()
                                }
                            }
                        )
                    }
                }


                Spacer(modifier = Modifier.padding(4.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Тут за задумом свжі події з сайту https://www.aa.kiev.ua/, " +
                                "а поки посилання на сайт",
                        modifier = Modifier.padding(8.dp)
                    )

                    Button(
                        onClick = { uriHandler.openUri("https://www.aa.kiev.ua/") }
                    ) {
                        Text("Анонімні алкоголіки Києва")
                    }
                }



                /* Spacer */
                Spacer(modifier = Modifier.padding(bottom = 132.dp))


            }
        }


    }
}


@Composable
private fun BookCard(
    title: String,
    imageIdRes: Int,
    description: String,
    onClickBookListener: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
//            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            .clickable { onClickBookListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(4.dp)
            )

            Image(
                painter = painterResource(imageIdRes),
                contentDescription = "image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
            )

        }

    }

}


