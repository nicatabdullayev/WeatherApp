package com.example.weatherappfinalproject.util

import android.content.Context

class SharedPreferences private constructor(private var context: Context) {
    private val WEATHR_PREFERENCES = "WEATHER"
    private val preferences: android.content.SharedPreferences
    private val editor: android.content.SharedPreferences.Editor

    //----------------------------------------------------------------------------------------------
    // Constructor and Setters
    //----------------------------------------------------------------------------------------------
    init {
        preferences =
            Companion.context.getSharedPreferences(WEATHR_PREFERENCES, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun putStringValue(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun putIntValue(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    fun putBooleanValue(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun putLongValue(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    val city: String?
        //----------------------------------------------------------------------------------------------
        get() = preferences.getString(CITY, "Baku")
    val numDays: String?
        get() = preferences.getString(NUM_DAYS, "7")
    val desc: String?
        get() = preferences.getString(DESC, "")
    val temp: String?
        get() = preferences.getString(TEMP, "")

    companion object {
        var instance: SharedPreferences? = null

        //----------------------------------------------------------------------------------------------
        // String Constants
        //----------------------------------------------------------------------------------------------
        const val CITY = "token"
        const val NUM_DAYS = "num_days"
        const val DESC = "desc"
        const val TEMP = "temp"
        fun getInstance(ctx: Context): SharedPreferences? {
            if (instance == null) {
                instance = SharedPreferences(ctx)
            }
            return instance
        }
    }
}
