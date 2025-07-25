package com.alexyach.compose.groupsaa.data.repository

import android.content.Context
import android.util.Log
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun loadDailyFromAssets(context: Context): Map<String, DailyReflections> {

    return try {
        val inputStream = context.assets.open("daily.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val type =
            object : TypeToken<Map<String, DailyReflections>>() {}.type
         Gson().fromJson(json, type)

    } catch (e: IOException) {
        Log.d("Logs", "loadDailyFromAssets IOException error: $e")
        emptyMap()
    } catch (e: JsonSyntaxException){
        Log.d("Logs", "loadDailyFromAssets JsonSyntaxException error: $e")
        emptyMap()
    }
}