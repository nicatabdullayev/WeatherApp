package com.delaroystudios.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class UviDb(lat: Double, lon: Double, value: Double) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("lat")
    var lat = 0.0

    @SerializedName("lon")
    var lon = 0.0

    @SerializedName("value")
    var value = 0.0
}
