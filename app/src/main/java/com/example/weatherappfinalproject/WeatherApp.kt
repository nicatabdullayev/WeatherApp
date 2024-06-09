package com.delaroystudios.weatherapp

import android.app.Activity
import android.app.Application
import com.delaroystudios.weatherapp.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import de.hdodenhof.circleimageview.BuildConfig

import javax.inject.Inject

class WeatherApp : Application() {
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
//            Timber.plant(DebugTree())
        }
        AppInjector.init(this)
    }

    fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}
