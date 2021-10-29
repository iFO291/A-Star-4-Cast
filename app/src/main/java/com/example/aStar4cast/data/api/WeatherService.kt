package com.example.aStar4cast.data.api

import com.example.aStar4cast.data.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather?")
    fun getWeather(
        @Query("q")q: String,
        @Query("units")units: String,
        @Query("lang")lang: String,
        @Query("appid")apikey: String
        ): Call<Weather>
}