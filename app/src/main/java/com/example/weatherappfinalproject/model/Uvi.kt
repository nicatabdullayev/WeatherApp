package com.delaroystudios.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Uvi {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("date_iso")
    @Expose
    var dateIso: String? = null

    @SerializedName("date")
    @Expose
    var date: Int? = null

    @SerializedName("value")
    @Expose
    var value: Double? = null
}
