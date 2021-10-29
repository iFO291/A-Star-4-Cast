package com.example.aStar4cast.data.model

import com.google.gson.annotations.SerializedName

class Main(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)