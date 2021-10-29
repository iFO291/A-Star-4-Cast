package com.example.aStar4cast.ui.weather

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.aStar4cast.data.api.RetrofitClient
import com.example.aStar4cast.data.sqlite.DatabaseHandler
import com.example.aStar4cast.data.sqlite.Favorite
import com.example.aStar4cast.data.model.Weather
import com.example.aStar4cast.data.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel : ViewModel() {

    var updateSuccess = false

    private var myFavoriteCity = ""

    var updateCityName = ""
    var updateDesc  = ""
    var updateUpdatedAt = ""
    var updateTemp = ""
    var updateTempMin =""
    var updateTempMax = ""
    var updateSunrise = ""
    var updateSunset= ""
    var updatePressure = ""
    var updateHumidity = ""
    var updateWind = ""
    var updateCityId = ""

    val uiCityName = MutableLiveData<String>()
    val uiDesc = MutableLiveData<String>()
    val uiTimeOfUpdate = MutableLiveData<String>()
    val uiTemp = MutableLiveData<String>()
    val uiTempMin = MutableLiveData<String>()
    val uiTempMax = MutableLiveData<String>()
    val uiSunrise = MutableLiveData<String>()
    val uiSunset = MutableLiveData<String>()
    val uiPressure = MutableLiveData<String>()
    val uiHumidity = MutableLiveData<String>()
    val uiWind = MutableLiveData<String>()
    val uiCityId = MutableLiveData<String>()
    val uiSuccess = MutableLiveData<Boolean>()

    private fun doUpdateWeather(aCityName: String) {
        val client = RetrofitClient()
        val retro = client.getClient()

        val units = "metric" // °C and weatherformat stuff
        val lang = "en" // choose language output from OpenWeatherMaps alternative
        val key = "" // Insert your own api-key here!

        val service = retro.create(WeatherService::class.java)
        val call = service.getWeather(aCityName, units, lang, key)

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weather = response.body()!!

                    uiSuccess.apply { value = response.isSuccessful }
                    uiCityName.apply { value = weather.name } //uiCityName.postValue(weather.name )
                    myFavoriteCity = weather.name
                    uiDesc.apply { value = weather.weather[0].description }
                    uiTimeOfUpdate.apply { value = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(weather.dt.toLong() * 1000)) }
                    uiTemp.apply { value = "${weather.main.temp}°C" }
                    uiTempMin.apply { value = "Min: ${weather.main.tempMin}°C" }
                    uiTempMax.apply { value = "Max: ${weather.main.tempMax}°C" }
                    uiSunrise.apply { value = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(weather.sys.sunrise.toLong() * 1000)) }
                    uiSunset.apply { value =  SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(weather.sys.sunset.toLong() * 1000)) }
                    uiPressure.apply { value = "${weather.main.pressure} mb" }
                    uiHumidity.apply { value = "${weather.main.humidity}%" }
                    uiWind.apply { value = weather.wind.speed.toString() + " m/s"}
                    uiCityId.apply { value = weather.id.toString() }
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {

            }
        })

        uiSuccess.postValue(updateSuccess)
        uiCityName.postValue(updateCityName)
        uiDesc.postValue(updateDesc)
        uiTimeOfUpdate.postValue(updateUpdatedAt)
        uiTemp.postValue(updateTemp)
        uiTempMin.postValue(updateTempMin)
        uiTempMax.postValue(updateTempMax)
        uiSunset.postValue(updateSunset)
        uiSunrise.postValue(updateSunrise)
        uiPressure.postValue(updatePressure)
        uiHumidity.postValue(updateHumidity)
        uiWind.postValue(updateWind)
    }

    fun getUpdateWeather(aCityName: String){
        doUpdateWeather(aCityName)
    }

    fun addFavoriteFromWeatherBtn(context: Context, city: String, cid: Int):Int {
        val db = DatabaseHandler(context)
        val data = db.readData()
        val favorite = Favorite(city, cid)
        for (i in 0 until data.size){
            if(data[i].myFavCity == city){
                Toast.makeText(context, "${data[i].myFavCity} is already a favorite!", Toast.LENGTH_SHORT).show()
                db.close()
                return 0
            }
        }
        db.insertData(favorite)
        db.close()
        return 1
    }
}
