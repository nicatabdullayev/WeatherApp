package com.example.weatherappfinalproject.ui.today

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import com.android.identity.android.legacy.Utility
import com.delaroystudios.weatherapp.di.Injectable
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.delaroystudios.weatherapp.model.UviDb
import com.example.weatherappfinalproject.R
import com.example.weatherappfinalproject.viewmodel.ForecastViewModel
import com.example.weatherappfinalproject.viewmodel.UviViewModel
import kotlinx.coroutines.channels.Channel
import java.util.Calendar
import javax.inject.Inject

class TodayFragment : Fragment(), Injectable {
    private var forecastViewModel: ForecastViewModel? = null
    private val uviViewModel: UviViewModel? = null

    @BindView(R.id.city)
    var mcity: TextView? = null

    @BindView(R.id.date)
    var date: TextView? = null

    @BindView(R.id.condition)
    var condition: TextView? = null

    @BindView(R.id.weather_resource)
    var weather_resource: ImageView? = null

    @BindView(R.id.temp_condition)
    var temp_condition: TextView? = null

    @BindView(R.id.temperature)
    var temperature: TextView? = null

    @BindView(R.id.humidity_value)
    var humidity_value: TextView? = null

    @BindView(R.id.wind_value)
    var wind_value: TextView? = null

    @BindView(R.id.uv_value)
    var uv_value: TextView? = null

    @Inject
    var viewModelFactory: Channel.Factory? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, root)
        requireActivity().window.statusBarColor =
            context?.let { ContextCompat.getColor(it, R.color.colorPrimaryToday) }!!
        fetchData()
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun fetchData() {
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        val city: String = SharedPreferences.getInstance(context).getCity()
        val numDays: String = SharedPreferences.getInstance(context).getNumDays()
        forecastViewModel =
            ViewModelProvider(this, viewModelFactory).get(ForecastViewModel::class.java)
        forecastViewModel!!.fetchResults(city, numDays).observe(viewLifecycleOwner) { result ->
            val dailyForecasts: List<SavedDailyForecast?>? = result.data
            mcity.setText(Utility.toTitleCase(city))
            if (dailyForecasts != null && dailyForecasts.size > 0) {
                dailyForecasts[0]?.let { dailyForecasts[0]?.let { it1 -> fetchUvi(it.lat, it1.lon) } }
                weather_resource!!.setImageResource(
                    Utility.getArtResourceForWeatherCondition(
                        dailyForecasts[0]?.weatherid
                    )
                )
                condition.setText(Utility.toTitleCase(dailyForecasts[0].description))
                date!!.text = java.lang.String.format(
                    "%s, %s", Utility.format(dailyForecasts[0].date), Utility.formatDate(
                        dailyForecasts[0]?.date
                    )
                )
                humidity_value?.setText(dailyForecasts[0].humidity.toString() + "%")
                wind_value.setText(
                    Utility.getFormattedWind(
                        context,
                        dailyForecasts[0]?.wind.toString().toFloat()
                    )
                )
                SharedPreferences.getInstance(context)
                    .putStringValue(SharedPreferences.DESC, dailyForecasts[0].description)
                if (timeOfDay >= 0 && timeOfDay < 12) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.morningTemp
                        )
                    )
                    SharedPreferences.getInstance(context).putStringValue(
                        SharedPreferences.TEMP, Utility.formatTemperature(
                            context, dailyForecasts[0]?.morningTemp
                        )
                    )
                    temperature.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.feelslikeMorning
                        )
                    )
                } else if (timeOfDay >= 12 && timeOfDay < 16) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.dayTemp
                        )
                    )
                    temperature.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.feelslikeDay
                        )
                    )
                    SharedPreferences.getInstance(context).putStringValue(
                        SharedPreferences.TEMP, Utility.formatTemperature(
                            context, dailyForecasts[0]?.morningTemp
                        )
                    )
                } else if (timeOfDay >= 16 && timeOfDay < 21) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.eveningTemp
                        )
                    )
                    temperature.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.eveningTemp
                        )
                    )
                    SharedPreferences.getInstance(context).putStringValue(
                        SharedPreferences.TEMP, Utility.formatTemperature(
                            context, dailyForecasts[0]?.morningTemp
                        )
                    )
                } else if (timeOfDay >= 21 && timeOfDay < 24) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.nightTemp
                        )
                    )
                    temperature.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0]?.feelslikeNight
                        )
                    )
                    SharedPreferences.getInstance(context).putStringValue(
                        SharedPreferences.TEMP, Utility.formatTemperature(
                            context, dailyForecasts[0]?.morningTemp
                        )
                    )
                }
            }
        }
    }

    private fun fetchUvi(lat: Double, lon: Double) {
        forecastViewModel?.fetchUvi(lat, lon)?.observe(viewLifecycleOwner) { result ->
            val uviDb: UviDb? = result.data
            if (uviDb != null) {
                uv_value?.setText(uviDb.value.toString() + "")
            }
        }
    }

    companion object {
        fun create(): TodayFragment {
            return TodayFragment()
        }
    }
}
