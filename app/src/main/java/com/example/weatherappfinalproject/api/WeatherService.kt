package com.delaroystudios.weatherapp.api

import androidx.lifecycle.LiveData
import com.delaroystudios.weatherapp.model.Uvi
import com.delaroystudios.weatherapp.model.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast/daily/")
    fun getWeatherForecast(
        @Query("q") city: String?,
        @Query("cnt") numDays: String?,
        @Query("units") units: String?,
        @Query("APPID") apiKey: String?,
    ): LiveData<ApiResponse<WeatherForecast?>?>?

    @GET("uvi")
    fun getUvi(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("appid") apiKey: String?,
    ): LiveData<ApiResponse<Uvi?>?>?
}
