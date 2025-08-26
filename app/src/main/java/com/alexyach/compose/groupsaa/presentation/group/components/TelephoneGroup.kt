package com.alexyach.compose.groupsaa.presentation.group.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreenAction
import com.alexyach.compose.groupsaa.presentation.group.GroupViewModel


@Composable
fun TelephoneGroup(
    group: Group,
    viewModel: GroupViewModel,
    context: Context
) {

    /**
     * КиевСтар:  067, 068, 077, 096, 097, 098;
     * Lifecell 063, 073, 093;
     * Vodafone (050, 066, 095, 099);
     * 3Mob (091),
     * PEOPLEnet (092),
     * Интертелеком (094)
     */


    val listTelephone = group.telephone.split(";")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        listTelephone.forEach {telephone ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.handlerGroupScreenAction(
                            GroupScreenAction.CallGroup(
                                context = context,
                                phoneNumber = group.telephone
                            )
                        )
                    }
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
            ) {

                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = Color.Green,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .size(60.dp)
                )

                Text(
                    text = telephone,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Image(
                    painter = if(telephone.contains("067") || telephone.contains("068") ||
                        telephone.contains("096") || telephone.contains("097") ||
                        telephone.contains("098")) {

                        painterResource(R.drawable.kyivstar)

                    } else if(telephone.contains("063") || telephone.contains("073") ||
                        telephone.contains("093")){

                        painterResource(R.drawable.lifecell_logo)

                    } else if(telephone.contains("050") || telephone.contains("066") ||
                        telephone.contains("095") || telephone.contains("099")) {

                        painterResource(R.drawable.vodafon)

                    } else {
                        painterResource(R.drawable.home_phone)
                    },
                    contentDescription = "Kyivstar",
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .size(50.dp)
                )

            }
            Spacer(Modifier.padding(4.dp))

        }

    }


}
