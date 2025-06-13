package com.alexyach.compose.groupsaa.presentation.group

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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
//        Log.d("Logs","makeCallGroup phoneNumber: $phoneNumber")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Log.d("Logs", "makeCall Error")
        }
    }

    fun showGroupMap(context: Context, group: Group) {
        val strLatLon: String = "geo:" + group.longitude.toString() + "," +
         group.latitude.toString()
//        Log.d("Logs", "showGroupMap= $strLatLon")
        val gmmIntentUri = strLatLon.toUri()
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        try {
            if(mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                Log.d("Logs", "no MAP")
            }
        } catch (e: android.content.ActivityNotFoundException) {
            Log.d("Logs", "Error: $e")
        }
    }

    fun showGroupMap(context: Context, addressForMap: String){
        val gmmIntentUri = Uri.parse("geo:0,0?q=$addressForMap")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        try {
            context.startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d("Logs", "Error addressForMap")
        }

    }





}


