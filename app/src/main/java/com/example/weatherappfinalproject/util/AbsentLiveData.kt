package com.example.weatherappfinalproject.util

import androidx.lifecycle.LiveData

/**
 * A LiveData class that has `null` value.
 */
class AbsentLiveData private constructor() : LiveData<Any?>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): AbsentLiveData {
            return AbsentLiveData()
        }
    }
}
