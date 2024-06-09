package com.delaroystudios.weatherapp.di

import android.app.Application
import android.provider.SyncStateContract
import androidx.room.Room.databaseBuilder
import com.delaroystudios.weatherapp.api.WeatherService
import com.delaroystudios.weatherapp.db.ForecastDao
import com.delaroystudios.weatherapp.db.WeatherDB
import com.delaroystudios.weatherapp.di.AppComponent.Builder
import com.example.weatherappfinalproject.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
internal class AppModule {
    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = Builder()
        httpClient.addInterceptor(logging)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(SyncStateContract.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient.build())
            .build()
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application?): WeatherDB {
        return databaseBuilder(app!!, WeatherDB::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideForecastDao(db: WeatherDB): ForecastDao? {
        return db.forecastDao()
    }
}
