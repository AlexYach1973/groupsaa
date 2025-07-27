package com.alexyach.compose.groupsaa.presentation.read

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R


@Composable
fun ReadScreen(
    paddingValues: PaddingValues
) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        val scrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {

            Text(
                text = stringResource(R.string.readscreen_iteratura),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.padding(4.dp))

            BookReadCard(
                titleIdRes = R.string.readscreen_book_bigaa,
                imageIdRes = R.drawable.book_bigaa,
                descriptionIdRes = R.string.readscreen_book_bigaa_descr,
                onClickBigAAListener = {
                    try {
                        uriHandler.openUri("https://www.aa.kiev.ua/knyga-anonimni-alkogoliky/")
                    } catch (e: Exception){
                        Toast.makeText(context, "Не вдалося", Toast.LENGTH_LONG).show()
                    }
                }
            )

            BookReadCard(
                titleIdRes = R.string.readscreen_book_12_12,
                imageIdRes = R.drawable.book_12_12,
                descriptionIdRes = R.string.readscreen_book_12_12_descr,
                onClickBigAAListener = {
                    try {
                        uriHandler.openUri("https://www.aa.kiev.ua/dvanadcyat-krokiv-anonimnix-alkogolikiv/")
                    } catch (e: Exception){
                        Toast.makeText(context, "Не вдалося", Toast.LENGTH_LONG).show()
                    }
                }
            )

            BookReadCard(
                titleIdRes = R.string.readscreen_book_live_sober,
                imageIdRes = R.drawable.book_live_soberly,
                descriptionIdRes = R.string.readscreen_book_live_sober_descr,
                onClickBigAAListener = {
                    try {
                        uriHandler.openUri("https://www.aa.kiev.ua/zhyty-tverezo/")
                    } catch (e: Exception){
                        Toast.makeText(context, "Не вдалося", Toast.LENGTH_LONG).show()
                    }
                }
            )

            /*BookReadCard(
                titleIdRes = R.string.readscreen_book_sees_bill,
                imageIdRes = R.drawable.book_sees_bill,
                descriptionIdRes = R.string.readscreen_book_sees_bill_descr,
                onClickBigAAListener = {
                    try {
                        uriHandler.openUri("https://www.aa.kiev.ua/zhyty-tverezo/")
                    } catch (e: Exception){
                        Toast.makeText(context, "Не вдалося", Toast.LENGTH_LONG).show()
                    }
                }
            )*/





        }
    }



}


@Composable
private fun BookReadCard(
     titleIdRes: Int,
     imageIdRes: Int,
     descriptionIdRes: Int,
     onClickBigAAListener: () -> Unit

) {
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
            .clickable { onClickBigAAListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Image(
                painter = painterResource(imageIdRes),
                contentDescription = "image",
                modifier = Modifier.size(80.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(titleIdRes),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
//                    fontFamily = FontFamily.Cursive
                )
//                Spacer(Modifier.padding(4.dp))

                Text(
                    text = stringResource(descriptionIdRes),
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }
    }


}













@Composable
private fun ReadReferencesCard(

) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            ),
//            .clickable { onClickHideListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.readscreen_library_kyiv),
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}


