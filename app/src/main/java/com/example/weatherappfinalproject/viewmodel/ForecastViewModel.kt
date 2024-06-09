package com.example.weatherappfinalproject.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.delaroystudios.weatherapp.model.Resource
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.delaroystudios.weatherapp.model.UviDb
import com.delaroystudios.weatherapp.repository.ForecastRepository
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    @VisibleForTesting
    fun fetchResults(
        city: String?,
        numDays: String?
    ): LiveData<Resource<List<SavedDailyForecast?>?>> {
        return forecastRepository.fetchForecast(city, numDays)
    }

    @VisibleForTesting
    fun fetchUvi(lat: Double?, lon: Double?): LiveData<Resource<UviDb?>> {
        return forecastRepository.fetchUvi(lat, lon)
    }
}
