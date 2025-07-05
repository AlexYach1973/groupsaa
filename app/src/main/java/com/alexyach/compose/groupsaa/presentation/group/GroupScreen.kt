package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group

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
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 50.dp)

                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer),
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        },

        ) { paddingValue ->
        val pv = paddingValue
        val scrollState = rememberScrollState()
        val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
//                .background(MaterialTheme.colorScheme.background)
                .padding(
//                    paddingValues = paddingValue)
                    top = 124.dp,
                    end = 8.dp,
                    start = 8.dp,
                    bottom = 132.dp
                )
                .fillMaxSize()
                .verticalScroll(scrollState),

            ) {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
////                    .background(MaterialTheme.colorScheme.secondaryContainer)
//            ) {
//                Text(
//                    text = group.name,
//                    style = MaterialTheme.typography.headlineSmall,
//                    color = MaterialTheme.colorScheme.primary,
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Bold,
////                    modifier = Modifier
////                        .background(Color.Green)
//                )
//            }

//            Spacer(modifier = Modifier.padding(8.dp))

            AddressGroup(
                context = context,
                viewModel = viewModel,
                group = group
            )
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

            if (group.note.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            brush = brush,
                            shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
                        )
                ) {
                    Text(
                        text = group.note,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
//                fontSize = 22.sp,
                    )
                }
            }
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
            .fillMaxWidth()
            .clickable {
                viewModel.makeCallGroup(
                    context = context,
                    phoneNumber = group.telephone
                )
            }
//            .background(brush = brush)
            .border(
                width = 2.dp,
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
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(70.dp)
//                .clip(shape = RoundedCornerShape(32.dp))
//                .background(MaterialTheme.colorScheme.tertiary)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = group.telephone,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

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
            .fillMaxWidth()
            .border(
                width = 2.dp,
                brush = brush,
                shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
            )
    ) {

        Icon(
            painter = painterResource(id = R.drawable.web),
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(50.dp)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
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
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

    }

}

@Composable
fun GroupSchedule(
    group: Group
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
//                color = Color.DarkGray,
                brush = brush,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        Icon(
            painterResource(R.drawable.handwatch),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(50.dp)
                .padding(start = 8.dp, end = 0.dp, top = 4.dp, bottom = 4.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.schedule,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

    }
}

@Composable
fun AddressGroup(
    context: Context,
    viewModel: GroupViewModel,
    group: Group
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.showGroupMap(
                    context = context,
                    addressForMap = group.addressForMap
                )
            }
            .border(
                width = 2.dp,
                brush = brush,
//                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                color = Color.DarkGray,
                shape = RoundedCornerShape(12.dp),
            )
    ) {
        Icon(
            painterResource(R.drawable.map1),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(50.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.addresses,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp)
            )
        }


    }
}

@Preview
@Composable
private fun ViewGroupScreen() {
    GroupScreen(
        Group("Obolon", "we 12", "18-00", "qw@wq", "123-3221", "BlaBlaBla", 1.1, 2.2),
        { }
    )
}