package com.example.aStar4cast.data.model

import com.google.gson.annotations.SerializedName

class Weather(
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    @SerializedName("weather")
    val weather: List<WeatherX>,
    val wind: Wind,
)