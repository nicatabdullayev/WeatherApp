package com.example.weatherappfinalproject.viewmodel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class WeatherViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    LayoutInflater.Factory {
    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        requireNotNull(creator) { "unknown model class $modelClass" }
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        TODO("Not yet implemented")
    }
}
