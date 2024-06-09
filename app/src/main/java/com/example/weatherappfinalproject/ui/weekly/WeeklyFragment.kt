package com.example.weatherappfinalproject.ui.weekly

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.identity.android.legacy.Utility
import com.delaroystudios.weatherapp.R
import com.delaroystudios.weatherapp.di.Injectable
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.example.weatherappfinalproject.R
import kotlinx.coroutines.channels.Channel
import java.util.Calendar
import javax.inject.Inject

class WeeklyFragment : Fragment(), Injectable, WeeklyAdapter.ItemClickListener {
    private var weeklyViewModel: ForecastViewModel? = null
    private var adapter: WeeklyAdapter? = null

    @BindView(R.id.recyclerview)
    var recyclerView: RecyclerView? = null

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

    @Inject
    var viewModelFactory: Channel.Factory? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_weekly, container, false)
        ButterKnife.bind(this, root)
        requireActivity().window.statusBarColor =
            context?.let { ContextCompat.getColor(it, R.color.weekly_background) }!!
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        adapter = WeeklyAdapter(context, this)
        recyclerView?.setAdapter(adapter)
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                recyclerView!!.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        fetchData()
        return root
    }

    private fun fetchData() {
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        val city: String = SharedPreferences.getInstance(context).getCity()
        val numDays: String = SharedPreferences.getInstance(context).getNumDays()
        weeklyViewModel =
            ViewModelProvider(this, viewModelFactory).get(ForecastViewModel::class.java)
        weeklyViewModel.fetchResults(city, numDays).observe(viewLifecycleOwner) { result ->
            val dailyForecasts: List<SavedDailyForecast> = result.data
            mcity.setText(Utility.toTitleCase(city))
            if (dailyForecasts != null && dailyForecasts.size > 0) {
                adapter!!.forecasts = dailyForecasts
                weather_resource!!.setImageResource(
                    Utility.getArtResourceForWeatherCondition(
                        dailyForecasts[0].weatherid
                    )
                )
                condition.setText(Utility.toTitleCase(dailyForecasts[0].description))
                date!!.text = java.lang.String.format(
                    "%s, %s",
                    Utility.format(dailyForecasts[0].date),
                    Utility.formatDate(dailyForecasts[0].date)
                )
                if (timeOfDay >= 0 && timeOfDay < 12) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0].morningTemp
                        )
                    )
                } else if (timeOfDay >= 12 && timeOfDay < 16) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0].dayTemp
                        )
                    )
                } else if (timeOfDay >= 16 && timeOfDay < 21) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0].eveningTemp
                        )
                    )
                } else if (timeOfDay >= 21 && timeOfDay < 24) {
                    temp_condition.setText(
                        Utility.formatTemperature(
                            context,
                            dailyForecasts[0].nightTemp
                        )
                    )
                }
                adapter!!.removeItem(0)
            }
        }
    }

    override fun onItemClickListener(itemId: Int) {}

    companion object {
        fun create(): WeeklyFragment {
            return WeeklyFragment()
        }
    }
}
