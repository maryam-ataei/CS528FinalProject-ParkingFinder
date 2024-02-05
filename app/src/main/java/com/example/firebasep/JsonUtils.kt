package com.example.firebasep

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.firebasep.DataModels.ParkingSpotsModel

fun parseJson(jsonString: String): List<ParkingSpotsModel> {
    val gson = Gson()
    val listType = object : TypeToken<List<ParkingSpotsModel>>() {}.type
    return gson.fromJson(jsonString, listType)
}