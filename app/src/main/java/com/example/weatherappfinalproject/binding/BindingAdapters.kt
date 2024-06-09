package com.delaroystudios.weatherapp.binding

import android.view.View
import android.widget.ImageView
import com.example.weatherappfinalproject.R
import com.squareup.picasso.Picasso

object BindingAdapters {
    @androidx.databinding.BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @androidx.databinding.BindingAdapter("bind:imageUrl")
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        if (imageUrl != null && imageUrl.length > 0) {
            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.broken_clouds)
                .into(view)
        }
    }
}
