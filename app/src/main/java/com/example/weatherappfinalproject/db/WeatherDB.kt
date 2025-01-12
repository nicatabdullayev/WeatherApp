package com.delaroystudios.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.delaroystudios.weatherapp.model.UviDb

@Database(entities = [SavedDailyForecast::class, UviDb::class], version = 1, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract fun forecastDao(): ForecastDao?
}
