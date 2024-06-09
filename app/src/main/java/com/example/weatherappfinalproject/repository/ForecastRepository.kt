package com.delaroystudios.weatherapp.repository

import androidx.lifecycle.LiveData
import com.delaroystudios.weatherapp.AppExecutors
import com.delaroystudios.weatherapp.api.ApiResponse
import com.delaroystudios.weatherapp.api.WeatherService
import com.delaroystudios.weatherapp.db.ForecastDao
import com.delaroystudios.weatherapp.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val forecastDao: ForecastDao,
    private val weatherService: WeatherService
) {

    fun loadForecast(city: String?, numDays: String?): LiveData<Resource<List<SavedDailyForecast>>> {
        return object : NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {
            override fun saveCallResult(item: WeatherForecast) {
                if (item.dailyForecasts != null) {
                    val savedDailyForecasts = item.dailyForecasts!!.map { forecast ->
                        SavedDailyForecast(
                            lat = item.city!!.coord!!.lat!!,
                            lon = item.city!!.coord!!.lon!!,
                            date = forecast.dt!!,
                            maxTemp = forecast.temp!!.max!!,
                            minTemp = forecast.temp!!.min!!,
                            dayTemp = forecast.temp!!.day!!,
                            eveningTemp = forecast.temp!!.eve!!,
                            morningTemp = forecast.temp!!.morn!!,
                            nightTemp = forecast.temp!!.night!!,
                            feelslikeDay = forecast.feelsLike!!.day!!,
                            feelslikeEve = forecast.feelsLike!!.eve!!,
                            feelslikeMorning = forecast.feelsLike!!.morn!!,
                            feelslikeNight = forecast.feelsLike!!.night!!,
                            humidity = forecast.humidity!!,
                            wind = forecast.speed!!,
                            description = forecast.weather!![0].description,
                            weatherid = forecast.weather!![0].id!!,
                            imageUrl = forecast.weather!![0].icon
                        )
                    }
                    forecastDao.insertForecastList(savedDailyForecasts)
                }
            }

            override fun shouldFetch(data: List<SavedDailyForecast>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<SavedDailyForecast?>?>? {
                return forecastDao.loadForecast()
            }

            override fun createCall(): LiveData<ApiResponse<WeatherForecast?>?>? {
                return weatherService.getWeatherForecast(
                    city,
                    numDays,
                    Unit,
                    WEATHER_API_KEY
                )
            }

            override fun onFetchFailed() {
                // Handle the error case here
            }
        }.asLiveData()
    }

    fun fetchForecast(city: String?, numDays: String?): LiveData<Resource<List<SavedDailyForecast>>> {
        return object : NetworkBoundResource<List<SavedDailyForecast>, WeatherForecast>(appExecutors) {
            override fun saveCallResult(item: WeatherForecast) {
                forecastDao.deleteNewsTable()
                if (item.dailyForecasts != null) {
                    val savedDailyForecasts = item.dailyForecasts!!.map { forecast ->
                        SavedDailyForecast(
                            lat = item.city!!.coord!!.lat!!,
                            lon = item.city!!.coord!!.lon!!,
                            date = forecast.dt!!,
                            maxTemp = forecast.temp!!.max!!,
                            minTemp = forecast.temp!!.min!!,
                            dayTemp = forecast.temp!!.day!!,
                            eveningTemp = forecast.temp!!.eve!!,
                            morningTemp = forecast.temp!!.morn!!,
                            nightTemp = forecast.temp!!.night!!,
                            feelslikeDay = forecast.feelsLike!!.day!!,
                            feelslikeEve = forecast.feelsLike!!.eve!!,
                            feelslikeMorning = forecast.feelsLike!!.morn!!,
                            feelslikeNight = forecast.feelsLike!!.night!!,
                            humidity = forecast.humidity!!,
                            wind = forecast.speed!!,
                            description = forecast.weather!![0].description,
                            weatherid = forecast.weather!![0].id!!,
                            imageUrl = forecast.weather!![0].icon
                        )
                    }
                    forecastDao.insertForecastList(savedDailyForecasts)
                }
            }

            override fun shouldFetch(data: List<SavedDailyForecast>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<SavedDailyForecast?>?>? {
                return this@ForecastRepository.forecastDao.loadForecast()
            }

            override fun createCall(): LiveData<ApiResponse<WeatherForecast?>?>? {
                return weatherService.getWeatherForecast(
                    city,
                    numDays,
                    WEATHER_API_KEY,
                    Unit
                )
            }

            override fun onFetchFailed() {
                // Handle the error case here
            }
        }.asLiveData()
    }

    fun fetchUvi(lat: Double?, lon: Double?): LiveData<Resource<UviDb>> {
        return object : NetworkBoundResource<UviDb, Uvi>(appExecutors) {
            override fun saveCallResult(item: Uvi) {
                forecastDao.deleteUvi()
                val uviDb = UviDb(
                    lat = item.lat!!,
                    lon = item.lon!!,
                    value = item.value!!
                )
                forecastDao.insertUvi(uviDb)
            }

            override fun shouldFetch(data: UviDb?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<UviDb?>? {
                return forecastDao.loadUvi()
            }

            override fun createCall(): LiveData<ApiResponse<Uvi?>?>? {
                return weatherService.getUvi(lat, lon, WEATHER_API_KEY)
            }

            override fun onFetchFailed() {
                // Handle the error case here
            }
        }.asLiveData()
    }

    companion object {
        const val WEATHER_API_KEY = "721a6c8230c041af071974a69f5a4c03"
        const val Unit = "" // Specify the unit if needed
    }
}
