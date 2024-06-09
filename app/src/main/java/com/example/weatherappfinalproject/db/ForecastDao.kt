package com.delaroystudios.weatherapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.delaroystudios.weatherapp.model.UviDb

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecastList(savedDailyForecasts: List<SavedDailyForecast?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUvi(uviDb: UviDb?)

    @Query("SELECT * FROM saveddailyforecast ORDER BY date ASC")
    fun loadForecast(): LiveData<List<SavedDailyForecast?>?>?

    @Query("SELECT * FROM uvidb ")
    fun loadUvi(): LiveData<UviDb?>?

    @Query("DELETE FROM saveddailyforecast")
    fun deleteNewsTable()

    @Query("DELETE FROM uvidb")
    fun deleteUvi()
}
