package com.example.aStar4cast.data.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    /**
     * CREATE DB AND TB
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTB = "CREATE TABLE $TB_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_FAVORITE TEXT," +
                "$COL_CITY_ID INTEGER)"
        db?.execSQL(createTB)
    }

    /**
     * DROP TABLE ON UPGRADE IF EXIST
     */
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TB_NAME")
    }

    /**
     * INSERT DATA INTO TABLE
     */
    fun insertData(favorite: Favorite) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_FAVORITE, favorite.myFavCity)
        cv.put(COL_CITY_ID, favorite.myCID)
        val result = db.insert(TB_NAME, null, cv)

        if (result == (0).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "A*stared ${favorite.myFavCity}", Toast.LENGTH_SHORT).show()
    }

    /**
     * READ DATA FROM TABLE
     */
    @SuppressLint("Range")
    fun readData() : MutableList<Favorite> {
        val fList: MutableList<Favorite> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TB_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val fav = Favorite()
                fav.myId = result.getString(result.getColumnIndex(COL_ID)).toInt()
                fav.myFavCity = result.getString(result.getColumnIndex(COL_FAVORITE))
                fav.myCID = result.getString(result.getColumnIndex(COL_CITY_ID)).toInt()
                fList.add(fav)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return fList
    }

    /**
     * EMPTY ALL FROM TB & IN DB
     */
    fun deleteAllData(): Int {
        val db = this.writableDatabase
        val result = db.delete(TB_NAME, null, null)
        db.close()
        return result
    }

    /**
     * DELETE ROW FROM LIST OF FAVORITES & IN DB
     */
    fun deleteOneRow(id: Int): Int{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_ID, id)
        val result = db.delete(TB_NAME, "$COL_ID=$id", null)
        db.close()
        return result
    }

    companion object {
        const val DB_NAME = "fav-data"
        const val TB_NAME = "favorites"
        const val COL_ID = "fav_id"
        const val COL_FAVORITE = "city"
        const val COL_CITY_ID = "city_id"
    }
}