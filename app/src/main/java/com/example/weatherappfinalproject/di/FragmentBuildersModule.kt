package com.delaroystudios.weatherapp.di


import com.example.weatherappfinalproject.ui.settings.SettingsFragment
import com.example.weatherappfinalproject.ui.today.TodayFragment
import com.example.weatherappfinalproject.ui.weekly.WeeklyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeTodayFragment(): TodayFragment?
    @ContributesAndroidInjector
    abstract fun contributeWeeklyFragment(): WeeklyFragment?
    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment?
}
