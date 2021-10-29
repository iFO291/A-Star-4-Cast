package com.example.aStar4cast.data.model

import com.google.gson.annotations.SerializedName

class WeatherX(
    val description: String,
    @SerializedName("icon")
    val icon: String,
    val id: Int,
//    val main: String
)