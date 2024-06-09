package com.delaroystudios.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class SavedDailyForecast(
    lat: Double,
    lon: Double,
    date: Long,
    maxTemp: Double,
    minTemp: Double,
    dayTemp: Double,
    eveningTemp: Double,
    morningTemp: Double,
    nightTemp: Double,
    feelslikeDay: Double,
    feelslikeEve: Double,
    feelslikeMorning: Double,
    feelslikeNight: Double,
    humidity: Int,
    wind: Double,
    description: String?,
    weatherid: Int,
    imageUrl: String?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @SerializedName("lat")
    var lat = 0.0

    @SerializedName("lon")
    var lon = 0.0

    @SerializedName("mdate")
    var date: Long = 0

    @SerializedName("temperatureMin")
    var minTemp = 0.0

    @SerializedName("temperatureMax")
    var maxTemp = 0.0

    @SerializedName("temperatureDay")
    var dayTemp = 0.0

    @SerializedName("temperatureNight")
    var nightTemp = 0.0

    @SerializedName("temperatureEvening")
    var eveningTemp = 0.0

    @SerializedName("temperatureMorning")
    var morningTemp = 0.0

    @SerializedName("feelslikeDay")
    var feelslikeDay = 0.0

    @SerializedName("feelslikeNight")
    var feelslikeNight = 0.0

    @SerializedName("feelslikeEvening")
    var feelslikeEve = 0.0

    @SerializedName("feelslikeMorning")
    var feelslikeMorning = 0.0

    @SerializedName("humidity")
    var humidity = 0

    @SerializedName("wind")
    var wind = 0.0

    @SerializedName("description")
    var description: String? = null

    @SerializedName("weatherid")
    var weatherid = 0

    @SerializedName("imageUrl")
    var imageUrl: String? = null
}
