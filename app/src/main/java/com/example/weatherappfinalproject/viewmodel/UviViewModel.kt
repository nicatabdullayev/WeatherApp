package com.example.weatherappfinalproject.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.delaroystudios.weatherapp.model.Resource
import com.delaroystudios.weatherapp.model.UviDb
import com.delaroystudios.weatherapp.repository.ForecastRepository
import javax.inject.Inject

class UviViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    @VisibleForTesting
    fun fetchUvi(lat: Double?, lon: Double?): LiveData<Resource<UviDb?>> {
        return forecastRepository.fetchUvi(lat, lon)
    }
}
