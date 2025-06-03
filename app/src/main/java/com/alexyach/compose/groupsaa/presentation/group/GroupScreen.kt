package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.domain.entity.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    group: Group,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: GroupViewModel = viewModel(
        factory = GroupViewModelFactory(group = group)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "${group.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(start = 8.dp, end = 8.dp)

        ) {
            Text(
                text = group.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = group.addresses,
                fontSize = 26.sp,
                fontFamily = FontFamily.Cursive
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = group.schedule,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))

            TelephoneGroup(
                context = context,
                viewModel = viewModel,
                group = group
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = group.email,
//                style = TextStyle(
//                    color = Color.DarkGray,
//                    fontSize = 16.sp,
//                    fontFamily = FontFamily.Monospace,
//                    fontWeight = FontWeight.W800,
//                    fontStyle = FontStyle.Italic,
//                    letterSpacing = 0.5.em,
//                    textDecoration = TextDecoration.Underline
//                )
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = group.note,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

}


@Composable
private fun TelephoneGroup(
    group: Group,
    viewModel: GroupViewModel,
    context: Context
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(12.dp)
//                shape = RectangleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(
            text = group.telephone,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    viewModel.makeCallGroup(
                        context = context,
                        phoneNumber = group.telephone
                    )
                }
        )
    }
}