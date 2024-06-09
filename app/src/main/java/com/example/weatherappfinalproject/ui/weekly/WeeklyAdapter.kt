package com.example.weatherappfinalproject.ui.weekly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.identity.android.legacy.Utility
import com.delaroystudios.weatherapp.model.SavedDailyForecast
import com.example.weatherappfinalproject.R
import java.util.Calendar

class WeeklyAdapter(
    private val mContext: Context?, // Member variable to handle item clicks
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<WeeklyAdapter.WeeklyViewHolder?>() {
    private var forecasts: List<SavedDailyForecast>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val view: View = LayoutInflater.from(mContext)
            .inflate(R.layout.weekly_items, parent, false)
        return WeeklyViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        // Determine the values of the wanted data
        val forecast: SavedDailyForecast = forecasts!![position]
        val max_temp: Double = forecast.maxTemp
        val min_temp: Double = forecast.minTemp
        val date: Long = forecast.date
        val description: String? = forecast.description
        val weather_id: Int = forecast.weatherid

        //Set values
        holder.desc.text = description
        holder.temp.setText(
            Utility.formatTemperature(
                mContext,
                forecast.maxTemp
            ) + "/" + Utility.formatTemperature(mContext, forecast.minTemp)
        )
        holder.imageView.setImageResource(Utility.getIconResourceForWeatherCondition(weather_id))
        holder.day.setText(Utility.format(forecast.date))
    }

    val itemCount: Int

        get() = if (forecasts == null) {
            0
        } else forecasts!!.size

    fun getForecasts(): List<SavedDailyForecast>? {
        return forecasts
    }

    fun setForecasts(forecastEntities: List<SavedDailyForecast>?) {
        forecasts = forecastEntities
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        forecasts.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: SavedDailyForecast, position: Int) {
        forecasts.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    interface ItemClickListener {
        fun onItemClickListener(itemId: Int)
    }

    // Inner class for creating ViewHolders
    inner class WeeklyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var day: TextView
        var temp: TextView
        var desc: TextView
        var imageView: ImageView

        init {
            day = itemView.findViewById<TextView>(R.id.day)
            temp = itemView.findViewById<TextView>(R.id.temp)
            desc = itemView.findViewById<TextView>(R.id.desc)
            imageView = itemView.findViewById<ImageView>(R.id.weather_img)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val elementId: Int = forecasts!![getAdapterPosition()].id
            mItemClickListener.onItemClickListener(elementId)
        }
    }
}
