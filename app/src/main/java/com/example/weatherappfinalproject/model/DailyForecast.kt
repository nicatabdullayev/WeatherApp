package com.delaroystudios.weatherapp.model

import com.example.weatherappfinalproject.model.FeelsLike
import com.example.weatherappfinalproject.model.Temp
import com.example.weatherappfinalproject.model.Weather
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DailyForecast {
    @SerializedName("dt")
    @Expose
    var dt: Long? = null

    @SerializedName("temp")
    @Expose
    var temp: Temp? = null

    @SerializedName("feels_like")
    @Expose
    private var feels_like: FeelsLike? = null

    @SerializedName("pressure")
    @Expose
    var pressure: Double? = null

    @SerializedName("humidity")
    @Expose
    var humidity: Int? = null

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("speed")
    @Expose
    var speed: Double? = null

    @SerializedName("deg")
    @Expose
    var deg: Int? = null

    @SerializedName("clouds")
    @Expose
    var clouds: Int? = null

    @SerializedName("rain")
    @Expose
    var rain: Double? = null
    var feelsLike: FeelsLike?
        get() = feels_like
        set(feelsLike) {
            feels_like = feelsLike
        }
}
