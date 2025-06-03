package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexyach.compose.groupsaa.domain.entity.Group
import androidx.core.net.toUri

class GroupViewModel(
    group : Group
) : ViewModel() {

    private val _screenState = MutableLiveData<GroupScreenState>(
        GroupScreenState.GroupItem(group = group))
    val screenState: LiveData<GroupScreenState> = _screenState


    fun makeCallGroup(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // Обработка случая, когда dialer не установлен
            // Например, показать сообщение пользователю
            Log.d("Logs", "makeCall Error")
        }
    }
}


