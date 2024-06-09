package com.delaroystudios.weatherapp.binding

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import javax.inject.Inject

class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @androidx.databinding.BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView?, url: String?) {
        if (imageView != null) {
            Glide.with(fragment).load(url).into(imageView)
        }
    }
}
