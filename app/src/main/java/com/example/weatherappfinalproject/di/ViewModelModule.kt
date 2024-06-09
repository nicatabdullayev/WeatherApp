package com.delaroystudios.weatherapp.di

import androidx.lifecycle.ViewModel
import com.example.weatherappfinalproject.viewmodel.ForecastViewModel
import com.example.weatherappfinalproject.viewmodel.WeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.channels.Channel

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    abstract fun bindForecastViewModel(forecastViewModel: ForecastViewModel?): ViewModel?
    @Binds
    abstract fun bindViewModelFactory(factory: WeatherViewModelFactory?): Channel.Factory?
}
