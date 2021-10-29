package com.example.aStar4cast.ui.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.aStar4cast.data.sqlite.DatabaseHandler

class FavoritesViewModel : ViewModel() {

    var favorites = ArrayList<String>()

    fun getDataBaseList(context: Context) {
        val db = DatabaseHandler(context)
        val data = db.readData()

        favorites  = ArrayList(data.size)
        for (i in 0 until data.size){
            favorites.add(data[i].myFavCity)
        }
    }

    fun getEmptyAllData(context: Context){
        val db = DatabaseHandler(context)

        db.deleteAllData()
        favorites.clear()
    }

    fun getDeleteOneRow(context: Context, item: String){
        val db = DatabaseHandler(context)
        val data = db.readData()

        for (i in 0 until data.size) {
            if (data[i].myFavCity == item) {
                db.deleteOneRow(data[i].myId)
            }
        }
    }
}