package com.alexyach.compose.groupsaa.data.repository

import android.content.Context
import android.util.Log
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class AssetsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadDailyFromAssets(): Map<String, DailyReflections> {

        return try {
            val inputStream = context.assets.open("daily.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            val type =
                object : TypeToken<Map<String, DailyReflections>>() {}.type
            Gson().fromJson(json, type)

        } catch (e: IOException) {
            Log.e("Logs", "loadDailyFromAssets IOException error: $e")
            emptyMap()

        } catch (e: JsonSyntaxException) {
            Log.e("Logs", "loadDailyFromAssets JsonSyntaxException error: $e")
            emptyMap()

        }catch (e: Exception) {
            Log.e("Logs", "Exception error: $e")
//            emptyMap()
            mapOf(
                "01-01" to DailyReflections(
                    title = "ERROR",
                    quote = e.cause.toString(),
                    discussion = e.message.toString()
                )
            )
        }

    }
}