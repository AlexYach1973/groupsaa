package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
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
                    Text(text = group.name)
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
        },

        ) { paddingValue ->
        val pv = paddingValue
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(
//                    paddingValues = paddingValue)
                    top = 88.dp,
                    end = 8.dp,
                    start = 8.dp,
                    bottom = 132.dp
                )
                .padding(start = 8.dp, end = 8.dp)
                .verticalScroll(scrollState),

            ) {
            Text(
                text = group.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AddressGroup(
                context = context,
                viewModel = viewModel,
                group = group
            )
//                Text(
//                    text = group.addresses,
//                    fontSize = 26.sp,
//                    fontFamily = FontFamily.Cursive
//                )
            Spacer(modifier = Modifier.padding(8.dp))

            GroupSchedule(group = group)
            Spacer(modifier = Modifier.padding(8.dp))


            TelephoneGroup(
                context = context,
                viewModel = viewModel,
                group = group
            )
            Spacer(modifier = Modifier.padding(8.dp))


            InternetLink(group = group)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = group.note,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = group.telephone,
//                enabled = false,
                readOnly = true,
                onValueChange = { },
                label = {
                    Text(text = "call")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(Color.Green)
                            .clickable {
                                viewModel.makeCallGroup(
                                    context = context,
                                    phoneNumber = group.telephone
                                )
                            }
                    )
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(width = 200.dp, height = 60.dp)

            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "TEXTTEXTTEXT",
                color = Color.Green,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "TEXTTEXTTEXT",
                color = Color.Blue,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "TEXTTEXTTEXT",
                color = Color.Red,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
            )

        }
    }


}


@Composable
private fun TelephoneGroup(
    group: Group,
    viewModel: GroupViewModel,
    context: Context
) {

    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//            .background(brush = brush)
            .padding(8.dp)
            .border(
                width = 1.dp,
//                color = Color.DarkGray,
                brush = brush,
                shape = RoundedCornerShape(12.dp),
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
                    Log.d("logs", "GroupScreen clickable")
                    viewModel.makeCallGroup(
                        context = context,
                        phoneNumber = group.telephone
                    )
                }
        )
    }
}

@Composable
fun InternetLink(
    group: Group
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//            .background(Color.Yellow)
//            .padding(16.dp)
            .border(
                width = 1.dp,
//                color = Color.DarkGray,
                brush = brush,
                shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
            )
    ) {

        Icon(
            painter = painterResource(id = R.drawable.web),
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier.padding(start = 8.dp)
        )

        Text(
            text = buildAnnotatedString {
                withLink(
                    LinkAnnotation.Url(
                        group.email,
                        TextLinkStyles(style = SpanStyle(color = Color.Blue))
                    )
                ) {
                    append(group.email)
                }
            },
            fontSize = 14.sp,
            modifier = Modifier
                .padding(8.dp)

        )
    }

}

@Composable
fun GroupSchedule(
    group: Group
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//            .background(Color.Yellow)
//            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
//                brush = brush,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        Icon(
            painterResource(R.drawable.handwatch),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(
            text = group.schedule,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(4.dp)

        )
    }
}

@Composable
fun AddressGroup(
    context: Context,
    viewModel: GroupViewModel,
    group: Group
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                viewModel.showGroupMap(
                    context = context,
                    addressForMap = group.addressForMap
                )
//                viewModel.showGroupMap(
//                    context = context,
//                    group = group
//                )
            }
//            .background(Color.Yellow)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
//                brush = brush,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        Icon(
            painterResource(R.drawable.map),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(
            text = group.addresses,
            fontSize = 26.sp,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier
                .padding(4.dp)

        )
    }
}