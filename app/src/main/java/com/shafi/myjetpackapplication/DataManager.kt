package com.shafi.myjetpackapplication

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.shafi.myjetpackapplication.models.Verses

object DataManager {
    var data = emptyArray<Verses>()
    var isDataLoaded = mutableStateOf(false)

    fun loadAssetsFromFile(context: Context) {
        val inputStream = context.assets.open("verse.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        data = gson.fromJson(json, Array<Verses> ::class.java)
        isDataLoaded.value = true
    }
}