package com.example.aStar4cast.data.sqlite

class Favorite{
    var myId: Int = 0
    var myFavCity: String = ""
    var myCID: Int = 0

    constructor(aFavCity: String, aCID: Int) {
        this.myFavCity = aFavCity
        this.myCID = aCID
    }
    constructor()
}