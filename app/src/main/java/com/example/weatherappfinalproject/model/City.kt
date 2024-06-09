package com.delaroystudios.weatherapp.model

import com.example.weatherappfinalproject.model.Coord
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("population")
    @Expose
    var population: Int? = null

    @SerializedName("timezone")
    @Expose
    var timezone: Int? = null
}
