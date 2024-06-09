package com.delaroystudios.weatherapp.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.delaroystudios.weatherapp.AppExecutors
import com.delaroystudios.weatherapp.api.ApiResponse
import com.delaroystudios.weatherapp.model.Resource
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.delaroystudios.weatherapp.model.Uvi
import com.delaroystudios.weatherapp.model.UviDb
import com.delaroystudios.weatherapp.model.WeatherForecast

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread constructor(
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType?>) {
        if (result.value != newValue) {
            result.value == newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            Resource.loading(newData)?.let { setValue(it) }
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            if (response.isSuccessful) {
                appExecutors.diskIO().execute {
                    processResponse(response)?.let { saveCallResult(it) }
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    setValue(Resource.error(response.errorMessage, newData))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<List<SavedDailyForecast?>?>?

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<WeatherForecast?>?>?
}
